#include<bits/stdc++.h>
using namespace std;
double error = 0.0001;
int x[100000];

double hornerr(int arr[],int n,int x){
    double ans=arr[0];
    for(int i=1;i<n;i++){
        ans=ans*x+arr[i];
    }
    return ans;
}
double f(double x)
{
    return pow(x, 3) - x - 1;
}
void bisection(double a, double b){
    double x,x1=a;
    //fx=hornerr(arr,degree,x);
    //fa=hornerr(arr,degree,a);
    //fb=hornerr(arr,degree,b);
    int i = 1;
    while(1)
    {
        x = (a + b) / 2;

        if(f(x) == 0)
            break;
        if(fabs((x-x1))<error){
            break;
        }
        if(f(a) * f(x) < 0)
            b = x;
        else a = x;
        x1=x;
        i++;
    }
    cout<<x<<endl;
}
int main(){

    double upBounf,lowBound;
    /*int degree;
    cout<<"Enter the highest degree of equation: ";
    cin>>degree;
    int x[degree];
    cout<<"Enter the value of coefficients: \nCoefficient x[3] = ";
    cin>>x[3];
    cout<<"Coefficient x[2] = ";
    cin>>x[2];
    cout<<"Coefficient x[1] = ";
    cin>>x[1];
    cout<<"Coefficient x[0] = ";
    cin>>x[0];
    */
    cout<<"Enter the value of xLower: ";
    cin>>lowBound;
    cout<<"Enter the value of xUpper: ";
    cin>>upBounf;

    bisection (lowBound,upBounf);

    return 0;
}
