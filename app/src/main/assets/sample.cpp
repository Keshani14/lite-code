/**
 * This is a comprehensive C++ sample file
 * demonstrating enhanced comment highlighting
 * 
 * Features:
 * - Single-line comments
 * - Multi-line comments
 * - C++ specific features
 * - Mixed code and comments
 */
#include <iostream>
#include <string>
#include <vector>
#include <memory>

// This is a single line comment
using namespace std;

/*
 * This is a multi-line comment
 * It spans multiple lines
 * and demonstrates block comment highlighting
 */

// Class declaration
class Calculator {
private:
    // Private member variables
    double result;
    
public:
    // Constructor
    Calculator() : result(0.0) {
        // Initialize result to zero
    }
    
    // Public methods
    void add(double value) {
        result += value; // Add value to result
    }
    
    void subtract(double value) {
        result -= value;
    }
    
    double getResult() const {
        return result;
    }
};

// Template function
template<typename T>
T maximum(T a, T b) {
    return (a > b) ? a : b;
}

int main() {
    // Variables and types
    int number = 42;
    string message = "Hello, C++!";
    vector<int> numbers = {1, 2, 3, 4, 5};
    
    /* 
     * Control structures section
     * This comment explains the following code block
     */
    if (number > 0) {
        cout << "Number is positive: " << number << endl;
    } else {
        cout << "Number is negative or zero" << endl;
    }
    
    // Loop with inline comment
    for (const auto& num : numbers) { // Range-based for loop
        cout << "Number: " << num << endl;
    }
    
    /*
     * Object-oriented programming demonstration
     * Shows C++ class usage
     */
    Calculator calc;
    calc.add(10.5);
    calc.subtract(2.3);
    
    // Smart pointer usage
    auto ptr = make_unique<string>("Smart pointer example");
    cout << *ptr << endl;
    
    // Template function call
    int maxInt = maximum(10, 20);
    double maxDouble = maximum(3.14, 2.71);
    
    cout << "Max int: " << maxInt << endl;
    cout << "Max double: " << maxDouble << endl;
    
    // End of main function
    return 0;
}
