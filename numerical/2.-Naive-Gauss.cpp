#include <bits/stdc++.h>
using namespace std;

// Function to perform Naive Gauss Elimination
void naiveGaussElimination(std::vector<std::vector<double>>& matrix) {
    int n = matrix.size();

    for (int i = 0; i < n; i++) {
        // Find the maximum row for the current column
        int maxRow = i;
        for (int k = i + 1; k < n; k++) {
            if (std::abs(matrix[k][i]) > std::abs(matrix[maxRow][i])) {
                maxRow = k;
            }
        }

        // Swap the rows
        if (maxRow != i) {
            for (int j = i; j <= n; j++) {
                std::swap(matrix[i][j], matrix[maxRow][j]);
            }
        }

        // Make all rows below this one 0 in the current column
        for (int k = i + 1; k < n; k++) {
            double factor = matrix[k][i] / matrix[i][i];
            for (int j = i; j <= n; j++) {
                matrix[k][j] -= factor * matrix[i][j];
            }
        }
    }

    // Back substitution to find the solution
    std::vector<double> solution(n);
    for (int i = n - 1; i >= 0; i--) {
        solution[i] = matrix[i][n];
        for (int j = i + 1; j < n; j++) {
            solution[i] -= matrix[i][j] * solution[j];
        }
        solution[i] /= matrix[i][i];
    }

    // Print the solution
    std::cout << "Solution of the system of equations:\n";
    for (int i = 0; i < n; i++) {
        std::cout << "x" << i + 1 << " = " << solution[i] << "\n";
    }
}

int main() {
    int n;
    std::cout << "Enter the number of equations: ";
    std::cin >> n;

    // Create an augmented matrix for the system of equations
    std::vector<std::vector<double>> matrix(n, std::vector<double>(n + 1));

    // Input the coefficients and constants of the equations
    std::cout << "Enter the coefficients and constants of the equations:\n";
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n + 1; j++) {
            std::cin >> matrix[i][j];
        }
    }

    // Perform Naive Gauss Elimination
    naiveGaussElimination(matrix);

    return 0;
}
