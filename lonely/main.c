#include <stdio.h>


int main()
{
    int a = 0;
    int b = 0;
    int a2 = 0;
    int b2 = 0;
    int max = 0;
    int min = 0;
    int resaulta = 0;
    int resaultb = 0;

    scanf("%d" , &a2);
    scanf("%d" , &b2);

    a = a2;
    b = b2;

    int rem;

    rem = a % 10;
    a /= 10;
    resaulta += rem * (1);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (2);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (4);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (8);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (16);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (32);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (64);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (128);

    rem = a % 10;
    a /= 10;
    resaulta += rem * (256);

//_________________________________________________________________________________________
    rem =0;

    rem = b % 10;
    b /= 10;
    resaultb += rem * (1);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (2);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (4);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (8);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (16);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (32);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (64);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (128);

    rem = b % 10;
    b /= 10;
    resaultb += rem * (256);

    max = resaulta ^ ((resaulta ^ resaultb)& -(resaulta < resaultb));
    int a10 = resaulta;
    int b10 = resaultb;

    printf ("%d\n",max);


    ( a10 % 2 == 1) ? printf("adade %d fard ast\n" , a10) : printf("adade %d zoj ast\n" , a10);
    ( b10 % 2 == 1) ? printf("adade %d fard ast\n" , b10) : printf("adade %d zoj ast\n" , b10);


    //____________________________________________________

    rem = 0;
    int a8_1 = 0;
    int b8_1 = 0;
    int a8_2 = 0;
    int b8_2 = 0;
    int a8_3 = 0;
    int b8_3 = 0;

    a = a2;
    b = b2;


    rem = a % 2;
    a = a/10;
    a8_1 += rem;


    rem = a % 2;
    a = a/10;
    a8_1 += rem*2;


    rem = a % 2;
    a = a/10;
    a8_1 += rem*4;


    rem = a % 2;
    a = a/10;
    a8_2 += rem;


    rem = a % 2;
    a = a/10;
    a8_2 += rem*2;


    rem = a % 2;
    a = a/10;
    a8_2 += rem*4;


    rem = a % 2;
    a = a/10;
    a8_3 += rem;


    rem = a % 2;
    a = a/10;
    a8_3 += rem*2;


    //___________________________________________________________

    rem = b % 10;
    b = b/10;
    b8_1 += rem;


    rem = b % 10;
    b = b/10;
    b8_1 += rem*2;


    rem = b % 10;
    b = b/10;
    b8_1 += rem*4;


    rem = b % 10;
    b = b/10;
    b8_2 += rem;


    rem = b % 10;
    b = b/10;
    b8_2 += rem*2;


    rem = b % 10;
    b = b/10;
    b8_2 += rem*4;


    rem = b % 10;
    b = b/10;
    b8_3 += rem;


    rem = b % 10;
    b = b/10;
    b8_3 += rem*2;

    printf("%d%d%d\n", a8_3 , a8_2,a8_1);
    printf("%d%d%d", b8_3 , b8_2,b8_1);

    return 0;
}

