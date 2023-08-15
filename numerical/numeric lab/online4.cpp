#include<bits/stdc++.h>
using namespace std;
int n=7;
double table[100][100],sum,value,x[100],y[100],X[100],Y[100],concentrationCL = 0,sumMSE=0;

void newtondivide()
{
    for(int i =0; i<n; i++)
    {
        table[i][0] = y[i];
    }
    for(int j=1; j<n; j++)
    {
        for(int i=0; i<n; i++)
        {
            table[i][j] = ((table[i+1][j-1] - table[i][j-1])/(x[i+j] - x[i]));
        }
    }
    for(int k=0; k<n; k++)
    {
        for(int i = 1; i < n; i++)
        {
            value = 1;
            for(int j = 0; j < i; j++)
            {
                value *= (X[k] - x[j]);
            }
            sum += (value * table[0][i]);
        }
        Y[k] = sum;
    }
    cout<<"T_degC | Dissolved Oxygen (mg/L) for temperature (degree Celsius) and concentration of chloride C"<<endl;
    for(int k=0; k<3; k++)
    {
        cout<<X[k]<<"\t\t"<<Y[k]/1000<<endl;
    }
}

void mse()
{
    for(int i =0; i<n; i++)
    {
        table[i][0] = y[i];
    }
    for(int j=1; j<n; j++)
    {
        for(int i=0; i<n; i++)
        {
            table[i][j] = ((table[i+1][j-1] - table[i][j-1])/(x[i+j] - x[i]));
        }
    }
    for(int k=0; k<n; k++)
    {
        for(int i = 1; i < n; i++)
        {
            value = 1;
            for(int j = 0; j < i; j++)
            {
                value *= (x[k] - x[j]);
            }
            sum += (value * table[0][i]);
        }
        Y[k] = sum/1000;
    }
    for(int k=0; k<n; k++)
    {
        sumMSE+=((Y[k]-y[k])*(Y[k]-y[k]));
    }
    sumMSE/=7;
    cout<<"Mean Squared Error (MSE) "<<sumMSE<<endl;
}
int main()
{
    cout<<"Enter the values : "<<endl;
    cout<<"T(degree) \t";
    cout<<" Y (concentration of CL) "<<endl;
    for(int i=0; i<n; i++)
    {
        cin>>x[i];
        cin>>y[i];
    }
    sum = y[0];
    cout<<"Interpolation points "<<endl;
    for(int i=0; i<n; i++)
    {
        cin>>X[i];
    }
    newtondivide();
    mse();
    return 0;
}

/*
0 12.9
5 11.3
10 10.1
15 9.03
20 8.17
25 7.46
30 6.86

40
45
50
55
60
65
70

*/
