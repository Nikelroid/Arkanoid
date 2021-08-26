
//agf_include "ctl_sketch_interpolants.glsl"

//agf_include "ctl_sketch_fragment_common.glsl"

const int maxPartition = 4;
const float g_textureScale = 3.5;
const float g_numDirQuant  = 8.0;
const float fAspect = 1.0;

struct largeStrokeTexcoord {
	vec2   tex1;			
	vec2   tex2;
	float    t;	 // up_rate, low_rate is 1 - low_rate
};

/// Core functions -------------------------------------------------------------
float color2gray(vec3 color) {
	return clamp(0.299*color.r+0.587*color.g+0.114*color.b,0,1);
}
float enhanceIntensity(float intensity, float enhancement) {
	return pow(intensity,enhancement);
}
vec2 toAspect(vec2 dir) {
	vec2 outDir = dir;
	outDir.x *= fAspect;
	return normalize(outDir);
}
vec2 fromAspect(vec2 dir) {
	vec2 outDir = dir;
	outDir.y *= fAspect;
	return normalize(outDir);
}
float quantizeAngle(float theta, float offsetTheta, out int partitionID) {   
	float thetaQ = 0;
	partitionID = 0;
	
	for(int i=0;i<g_numDirQuant;i++) {
		float thetamin = pi*(i/g_numDirQuant-1/g_numDirQuant/2.0)+offsetTheta;
		float thetamax = pi*(i/g_numDirQuant+1/g_numDirQuant/2.0)+offsetTheta;     

		if(thetamin <= theta && theta < thetamax) {           
			thetaQ = pi*(i/g_numDirQuant) + offsetTheta;
			partitionID = i;

		}
	}        
	return thetaQ;
}

vec2 getRotTex(float theta, vec2 tex)
{		
	float c = cos(theta); float s = sin(theta);

	vec2 aspectCS = vec2(c,s);
	vec2 cs = fromAspect(aspectCS);

	vec2 scaledTex = tex*g_textureScale + vec2(100.0,100.0);

	c = cs.x;
	s = cs.y;
	vec2 rotTex = vec2(c*scaledTex.x - s*scaledTex.y, s*scaledTex.x + c*scaledTex.y);  	

	return rotTex;
}
	
largeStrokeTexcoord texcoordLargeStrokeTexture(vec2 tex, float intensity) {
	largeStrokeTexcoord texinfo;
	
	int lowindexX, lowindexY;
	int upindexX, upindexY;
	int lowlevel, uplevel, temp;
		
	// assume  0 <= intensity <= 1, and we have 16 stroke texture			
	float Ilevel = abs(intensity) * 15;
		
	lowlevel = int(floor(Ilevel));
	uplevel  = int(ceil(Ilevel));
		
	if(lowlevel < 0)
		lowlevel = 0;
	if(uplevel > 15)
		uplevel = 15;
	if(uplevel < lowlevel) {
		temp = uplevel;
		uplevel = lowlevel;
		lowlevel = temp;
	}
		
	lowindexY = lowlevel / 4;
	lowindexX =  int(mod(lowlevel, 4));
	upindexY  = uplevel / 4;
    upindexX  = int(mod(uplevel, 4));

	tex = clamp(tex-1/128.0,0,1);
	
	float factor = 127.0/512.0;
	texinfo.tex1 = vec2(0.25 * lowindexX + factor*tex.x + psize/2.0, 0.25*lowindexY + factor*tex.y + psize/2.0);
	texinfo.tex2 = vec2(0.25 * upindexX  + factor*tex.x + psize/2.0, 0.25*upindexY  + factor*tex.y + psize/2.0);
	texinfo.t = abs(Ilevel - lowlevel);  
	
	return texinfo;
}
uniform sampler2D SamplerNormal;
uniform sampler2D SamplerColor;
uniform sampler2D StrokeDir;
uniform sampler2D SamplerPattern;
uniform mat4x4  agf_TileUVToDocumentUV;

void main () {

	/////////////////////////////// hatchingQuantizedDirection part ////////////////////////
    vec2 tex = vertOut_fTexCoord0.xy;
    vec4 tex4 = vec4(tex.x, tex.y, 0.0, 1.0);
   vec4 doctex4 = agf_TileUVToDocumentUV*tex4;
   vec2 doctex = doctex4.xy;
	vec4 colorOrg = texture2D(SamplerColor, tex);

//			gl_FragColor = vec4(1,0,0,colorOrg.a);
	//		return;

//			gl_FragColor = vec4(texture2D(StrokeDir, tex).rgb,1.0);//colorOrg.a); 
//			return;


	//Don't hatch backgrounds
	float depth = texture2D(SamplerNormal, tex).w;
	if(depth == 0.0) {
		gl_FragColor = vec4(1,1,1,colorOrg.a);
		return ;
	}
	
	//color
	float intensity = color2gray(colorOrg.rgb);
	float g_intensityEnhancement =3.0;
	intensity = enhanceIntensity(intensity,g_intensityEnhancement);
	vec4 color = vec4(0,0,0,1.0);

	vec4 dirTexSample = texture2D(StrokeDir, tex);

			//	gl_FragColor = vec4(SamplerPattern.rgb,colorOrg.a);
			//	return;

	//These directions are normalized already.
	vec2 rotMin = normalize(dirTexSample.xy);
	vec2 rotMax = normalize(dirTexSample.zw);

	//Make directions be in [0,pi].
	if(rotMin.y<0) rotMin = -rotMin;
	if(rotMax.y<0) rotMax = -rotMax;   

	////Make directions be in [0,pi).
	if(abs(rotMin.y)<=0.001 && rotMin.x<0) rotMin = -rotMin;
	if(abs(rotMax.y)<=0.001 && rotMax.x<0) rotMax = -rotMax;   

	float deltaTheta = pi/float(g_numDirQuant);

	float brightIntensity = sqrt(clamp(intensity, 0.5, 1.0));		
	vec2 aspectRotMin = toAspect(rotMin);
	vec2 aspectRotMax = toAspect(rotMax);
	float minTheta = acos(clamp(aspectRotMin.x,-1.0,1.0));
	float maxTheta = acos(clamp(aspectRotMax.x,-1.0,1.0));

	color = vec4(1,1,1,1);
	vec2 minStrokeTexcoord[4];
    minStrokeTexcoord[0]=vec2(0,0);
    minStrokeTexcoord[1]=vec2(0,0);
    minStrokeTexcoord[2]=vec2(0,0);
    minStrokeTexcoord[3]=vec2(0,0);
    
	vec2 maxStrokeTexcoord[4];
    maxStrokeTexcoord[0]=vec2(0,0);
    maxStrokeTexcoord[1]=vec2(0,0);
    maxStrokeTexcoord[2]=vec2(0,0);
    maxStrokeTexcoord[3]=vec2(0,0);

	for(int i=0;i<maxPartition;i++) {
		float offsetTheta = deltaTheta*i/(float(maxPartition));

		int partitionID;

		float tempTheta = minTheta;
		if(tempTheta>=pi-deltaTheta/2.0+offsetTheta)
			tempTheta -= pi;
		if(tempTheta<-deltaTheta/2.0+offsetTheta)
			tempTheta += pi;

		float minThetaQ = quantizeAngle(tempTheta, offsetTheta, partitionID);

		float residual = minThetaQ - tempTheta;

		float weight1 = clamp(1-abs(residual/deltaTheta*2),0,1);
		float weight2 = 1-weight1; 		

		vec2 rawRotTex = getRotTex(minThetaQ, doctex);
		vec2 newTex = rawRotTex-floor(rawRotTex);
		largeStrokeTexcoord Ltex = texcoordLargeStrokeTexture(newTex, brightIntensity);
		vec4 value = mix(texture2D(SamplerPattern, Ltex.tex1 - floor(Ltex.tex1)), texture2D(SamplerPattern, Ltex.tex2 -floor(Ltex.tex2)), Ltex.t);
		color *= weight1 * value + weight2;

		if(intensity < 0.5) {
			float darkIntensity = sqrt(clamp(intensity*2, 0.0, 1.0));
			tempTheta = maxTheta;
			if(tempTheta>=pi-deltaTheta/2.0+offsetTheta)
				tempTheta -= pi;
			if(tempTheta<-deltaTheta/2.0+offsetTheta)
				tempTheta += pi;

			float maxThetaQ = quantizeAngle(tempTheta, offsetTheta, partitionID);

			residual = maxThetaQ - tempTheta;
			weight1 = clamp(1-abs(residual/deltaTheta*2),0,1);
			weight2 = 1-weight1; 		

			vec2 rawRotTex = getRotTex(maxThetaQ, doctex);
			vec2 newTex = rawRotTex-floor(rawRotTex);
			largeStrokeTexcoord Ltex = texcoordLargeStrokeTexture(newTex, darkIntensity);
			vec4 value = mix(texture2D(SamplerPattern, Ltex.tex1 - floor(Ltex.tex1)), texture2D(SamplerPattern, Ltex.tex2 - floor(Ltex.tex2)), Ltex.t);
			color *= weight1 * value + weight2;
		}
	}

	color.a = colorOrg.a;
	gl_FragColor = color;

}


//frag2buffer composeFrag(in vertex2frag interpolant, 			
//			uniform sampler2D SamplerNormal,
//			uniform sampler2D SamplerColor,
//			uniform sampler2D SamplerPosition,
//			uniform sampler2D StrokeDir,
//			uniform sampler2D SamplerPattern,
//			uniform mat4x4  agf_CameraModelViewMatrixInverted,
//			uniform mat4x4  agf_CameraMVPMatrixInverted,
//			uniform mat4x4  agf_TileUVToDocumentUV) {
//
//	frag2buffer fragOut;
//
//	/////////////////////////////// hatchingQuantizedDirection part ////////////////////////
//	vec2 tex = interpolant.0TexCoord0.xy;
//   vec4 tex4 = vec4(tex.x, tex.y, 0.0, 1.0);
//   vec4 doctex4 = mul(agf_TileUVToDocumentUV,tex4);
//   vec2 doctex = doctex4.xy;
//	vec4 colorOrg = texture2D(SamplerColor, tex);
//
//	//Don't hatch backgrounds
//	float depth = texture2D(SamplerNormal, tex).w;
//	if(depth == 0.0) {
//		fragOut.color = vec4(1,1,1,colorOrg.a);
//		return fragOut;
//	}
//	
//	//color
//	float intensity = color2gray(colorOrg.rgb);
//	float g_intensityEnhancement =3.0;
//	intensity = enhanceIntensity(intensity,g_intensityEnhancement);
//	vec4 color = vec4(0,0,0,1.0);
//
//	vec4 dirTexSample = texture2D(StrokeDir, tex);
//
//	//These directions are normalized already.
//	vec2 rotMin = normalize(dirTexSample.xy);
//	vec2 rotMax = normalize(dirTexSample.zw);
//
//	//Make directions be in [0,pi].
//	if(rotMin.y<0) rotMin = -rotMin;
//	if(rotMax.y<0) rotMax = -rotMax;   
//
//	////Make directions be in [0,pi).
//	if(abs(rotMin.y)<=0.001 && rotMin.x<0) rotMin = -rotMin;
//	if(abs(rotMax.y)<=0.001 && rotMax.x<0) rotMax = -rotMax;   
//
//	float deltaTheta = pi/float(g_numDirQuant);
//
//	float brightIntensity = sqrt(clamp(intensity, 0.5, 1.0));		
//	vec2 aspectRotMin = toAspect(rotMin);
//	vec2 aspectRotMax = toAspect(rotMax);
//	float minTheta = acos(clamp(aspectRotMin.x,-1.0,1.0));
//	float maxTheta = acos(clamp(aspectRotMax.x,-1.0,1.0));
//
//	color = vec4(1,1,1,1);
//	vec2 minStrokeTexcoord[4] = {vec2(0,0),vec2(0,0),vec2(0,0),vec2(0,0)};
//	vec2 maxStrokeTexcoord[4] = {vec2(0,0),vec2(0,0),vec2(0,0),vec2(0,0)};
//
//	for(int i=0;i<maxPartition;i++) {
//		float offsetTheta = deltaTheta*i/(float)maxPartition;
//
//		int partitionID;
//
//		float tempTheta = minTheta;
//		if(tempTheta>=pi-deltaTheta/2.0+offsetTheta)
//			tempTheta -= pi;
//		if(tempTheta<-deltaTheta/2.0+offsetTheta)
//			tempTheta += pi;
//
//		float minThetaQ = quantizeAngle(tempTheta, offsetTheta, partitionID);
//
//		float residual = minThetaQ - tempTheta;
//
//		float weight1 = clamp(1-abs(residual/deltaTheta*2),0,1);
//		float weight2 = 1-weight1; 		
//
//		vec2 rawRotTex = getRotTex(0, minThetaQ, doctex);
//		vec2 newTex = rawRotTex-floor(rawRotTex);
//		largeStrokeTexcoord Ltex = texcoordLargeStrokeTexture(newTex, brightIntensity);
//		vec4 value = mix(texture2D(SamplerPattern, Ltex.tex1 - floor(Ltex.tex1)), texture2D(SamplerPattern, Ltex.tex2 -floor(Ltex.tex2)), Ltex.t);
//		color *= weight1 * value + weight2;
//
//		if(intensity < 0.5) {
//			float darkIntensity = sqrt(saturate(intensity*2));	
//			tempTheta = maxTheta;
//			if(tempTheta>=pi-deltaTheta/2.0+offsetTheta)
//				tempTheta -= pi;
//			if(tempTheta<-deltaTheta/2.0+offsetTheta)
//				tempTheta += pi;
//
//			float maxThetaQ = quantizeAngle(tempTheta, offsetTheta, partitionID);
//
//			residual = maxThetaQ - tempTheta;
//			weight1 = clamp(1-abs(residual/deltaTheta*2),0,1);
//			weight2 = 1-weight1; 		
//
//			vec2 rawRotTex = getRotTex(0, maxThetaQ, doctex);
//			vec2 newTex = rawRotTex-floor(rawRotTex);
//			largeStrokeTexcoord Ltex = texcoordLargeStrokeTexture(newTex, darkIntensity);
//			vec4 value = mix(texture2D(SamplerPattern, Ltex.tex1 - floor(Ltex.tex1)), texture2D(SamplerPattern, Ltex.tex2 - floor(Ltex.tex2)), Ltex.t);
//			color *= weight1 * value + weight2;
//		}
//	}
//
//	color.a = colorOrg.a;
//	fragOut.color = color;
//	return fragOut;
//}
//
