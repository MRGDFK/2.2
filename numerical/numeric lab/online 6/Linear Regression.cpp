#include<bits/stdc++.h>
using namespace std;

int n;
double x, y, sumX=0, sumY=0, sumXX=0, sumYY=0, sumXY=0,xBar, yBar, denom, a, b,sumX2=0, sumY2=0, sumXX2=0, sumYY2=0, sumXY2=0;
double X[50], Y[50],XP[50],YP[50];

int main(){
    cout<<"Input number of data points: ";
    cin>>n;
    cout<<endl<<"Data values of x and y: "<<endl;
    for(int i = 1; i <= n; i++){
        cin>>X[i]>>Y[i];
    }
    for(int i = 1; i <= n; i++){
        sumX += X[i];
        sumY += Y[i];
        sumXX += pow(X[i], 2);
        sumYY += pow(Y[i], 2);
        sumXY += X[i]*Y[i];
    }
    xBar = sumX / n;
    yBar = sumY / n;
    denom = n * sumXX - pow(sumX, 2);
    if(denom != 0){
        b = (n*sumXY - sumX*sumY)/denom;
        a = yBar - b*xBar;
        cout<<endl<<"Linear Regression line:\n\t y = "<<a;
        if(b < 0)
            cout<<" - "<<fabs(b)<<"x";
        else if(b > 0)
            cout<<" + "<<b<<"x";
        else
            cout<<endl;
    for(int i = 1; i <= n; i++){
        XP[i]=2019+i;
    }
    cout<<endl<<endl<<"Year"<<"\t"<<"Approximate Average Temperature in Degree Celsius"<<endl<<endl;
    for(int i = 1; i <= n; i++){
        YP[i] = a + b*XP[i];
        cout<<XP[i]<<"\t\t"<<YP[i]<<endl;
    }
    for(int i = 1; i <= n; i++){
        sumX2 += XP[i];
        sumY2 += YP[i];
        sumXX2 += pow(XP[i], 2);
        sumYY2 += pow(YP[i], 2);
        sumXY2 += XP[i]*YP[i];
    }
        double R1= (21.00*sumXY2) - (sumX2*sumY2);
        double R2= sqrt((21.00*sumXX2 - pow(sumX2,2))*(21.00*sumYY2 - pow(sumY2,2)));
        double R= R1/R2;
        cout<<endl<<"Here R = "<<R<<endl;
        if(R < 0){
            cout<<"Strong Negative Relation."<<endl;
        }
        else if(R == 0){
            cout<<"No Relation."<<endl;
        }
        else{
            cout<<"Strong Positive Relation."<<endl;
        }
    }
    else{
        cout<<"No solution."<<endl;
    }
    return 0;
}

/*
1993 25.00317
1994 25.26167
1995 25.41583
1996 25.44025
1997 24.85925
1998 25.52417
1999 25.71483
2000 25.15017
2001 25.337
2002 25.38033
2003 25.28083
2004 25.38633
2005 25.532
2006 25.76567
2007 25.34375
2008 25.3895
2009 25.90492
2010 25.94033
2011 25.20508
2012 25.53358
2013 25.9675

*/
