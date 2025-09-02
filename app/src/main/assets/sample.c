/**
 * This is a comprehensive C sample file
 * demonstrating enhanced comment highlighting
 * 
 * Features:
 * - Single-line comments
 * - Multi-line comments
 * - Mixed code and comments
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// This is a single line comment
#define MAX_SIZE 100

/*
 * This is a multi-line comment
 * It spans multiple lines
 * and demonstrates block comment highlighting
 */

// Function declarations
int add(int a, int b);
void printMessage(const char* message);

int main() {
    // Variables and types
    int number = 42;
    char message[] = "Hello, C!";
    float pi = 3.14159f;
    double precision = 3.14159265359;
    
    /* 
     * Control structures section
     * This comment explains the following code block
     */
    if (number > 0) {
        printf("Number is positive: %d\n", number);
    } else {
        printf("Number is negative or zero\n");
    }
    
    // Loop with inline comment
    for (int i = 0; i < 3; i++) { // Loop counter
        printf("Count: %d\n", i);
    }
    
    /*
     * Function calls demonstration
     * Shows how to call functions
     */
    int result = add(10, 20);
    printMessage("Function call successful!");
    
    // End of main function
    return 0;
}

/**
 * Function to add two integers
 * @param a first integer
 * @param b second integer
 * @return sum of a and b
 */
int add(int a, int b) {
    // Simple addition
    return a + b;
}

/**
 * Function to print a message
 * @param message string to print
 */
void printMessage(const char* message) {
    // Print the message
    printf("%s\n", message);
}
