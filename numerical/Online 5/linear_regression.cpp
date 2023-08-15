#include <bits/stdc++.h>

using namespace std;

void Linear_Regression()
{
    cout << "Enter the number of n" << endl;
    int n;
    cin >> n;

    double x[n], y[n];
    double x1, y1;

    for (int c = 1; c <= n; c++)
    {
        cin >> x1 >> y1;

        x[c] = x1;
        y[c] = y1;
    }
    double sumX = 0, sumX2 = 0, sumY = 0, sumXY = 0;

    for (int c = 1; c <= n; c++)
    {
        sumX = sumX + x[c];
        sumX2 = sumX2 + (x[c] * x[c]);
        sumY = sumY + y[c];
        sumXY = sumXY + (x[c] * y[c]);
    }

    double b = (((n * sumXY) - (sumX * sumY)) / ((n * sumX2) - (sumX * sumX)));
    double a = (sumY - (b * sumX)) / n;

    cout << "a is :" << a << endl;
    cout << "b is :" << b << endl;
}

int main()
{
    Linear_Regression();
}
