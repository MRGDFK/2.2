#include<bits/stdc++.h>

using namespace std;

void Curve_Fitting_Algorithm()
{
    cout<<"Enter the number of input"<<endl;
    int n;
    cin>>n;

    double x[n],y[n];

    double x1,y1;

    for(int c=1;c<=n;c++)
    {
        cin>>x1>>y1;
        x[c] = x1;
        y[c] = y1;
    }

    double sumX = 0,sumX2 = 0,sumY = 0,sumXY = 0;

    for(int c=1;c<=n;c++)
    {
        sumX = sumX + log(x[c]);

        sumX2 = sumX2 +(log(x[c])*log(x[c]));

        sumY = sumY + log(y[c]);

        sumXY = sumXY + (log(x[c])*log(y[c]));
    }

    double b = (((n*sumXY)-(sumX*sumY))/((n*sumX2)-(sumX*sumX)));
    double a = (sumY-(b*sumX))/n;

    cout<<"a is :"<<a<<endl;
    cout<<"b is :"<<b<<endl;
}
int main()
{
    Curve_Fitting_Algorithm();
}
