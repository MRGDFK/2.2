#include<bits/stdc++.h>
#include <math.h>
using namespace std;

int degree, eq[100], j = 0;
double e, x[100];

double horner(double x)
{
    double p;
    p = eq[0];
    for(int i = 1; i <=degree; i++){
        p = p*x +eq[i];
    }
    return p;
}
double secant(){
    double fx1, fx2, fx, x1, x2;
    int i = 2;
    x1 = x[0];//x(i-1)
    x2 = x[1];//x(i)
    fx1 = horner(x1);
    fx2 = horner(x2);
    while(1){
        j++;
        x[i] = x2 -((fx2*(x2-x1))/(fx2-fx1));
        fx = horner(x[i]);//x(i+1)

        if(fx == 0){
            cout<<"Root Found Using Secant Method Method is Exact Root"<<endl;
            return x[i];
        }
        else{
            i++;
            x2 = x[i-1];
            x1 = x[i-2];
            fx2 = horner(x2);
            fx1 = horner(x1);
        }
        if(fabs(fx)<e)
        {
            cout<<"Root Found Using Newton Secant Method Method is Not Exact Root"<<endl;
            return x2;
        }
    }
}
int main(){
    double root;
    cout<<"ENTER THE TOTAL NO. OF POWER: ";
    cin>>degree;
    cout<<"\n";
    for(int i = 0; i <=degree; i++){
        cout<<"x^"<<i<<"::";
        cin>>eq[i];
        cout<<endl;
    }
    cout<<"THE POLYNOMIAL IS ::: "<<eq[0]<<"x^2"<<eq[1]<<"x"<<eq[2]<<endl;
    cout<<"INTIAL X0----> ";
    cin>>x[0];
    cout<<"X1----> ";
    cin>>x[1];

    printf("Enter the value of error: ");
    cin>>e;
    cout<<endl;
    root = secant();
    cout<<endl;
    cout<<"The root: "<<root<<endl;
    return 0;
}
