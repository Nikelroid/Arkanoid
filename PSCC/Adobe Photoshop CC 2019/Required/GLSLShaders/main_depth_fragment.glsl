
//agf_include "main_vertex2fragment.glsl"


uniform vec4 clippingPlane;

void main()
{
    float clipSign = dot(clippingPlane.xyz, vertOut_vPosition.xyz)+clippingPlane.w;
    
	if (clipSign < 0.0) discard;

	vec4 clipCoord = vertOut_fClipPosition;
	clipCoord = clipCoord/clipCoord.w;
	float depth = (clipCoord.z+1.0)*0.5;

	gl_FragColor.rgb = vec3(depth, depth, depth);
	gl_FragColor.a = 1.0;

}