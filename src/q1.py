from scipy.optimize import linprog

# Using Scipy method of linprog to calculate the function

# Coefficients of the objective function
c = [3, 8]  # Minimize 3x + 8y

# Coefficients for the inequality constraints
A = [
    [-1, -1],  # -x - y <= -8 (equivalent to x + y >= 8)
    [2, -3],   # 2x - 3y <= 0
    [1, 2],    # x + 2y <= 30
    [3, -1],   # 3x - y <= 0
    [1, 0],    # x <= 10
    [0, 1],    # y <= 9
]

b = [-8, 0, 30, 0, 10, 9]

x = (0, None)
y = (0, None)

res = linprog(c, A_ub=A, b_ub=b, bounds=[x, y], method='highs')


if res.success:
    print(f"Optimal value for x: {res.x[0]:.2f}")
    print(f"Optimal value for y: {res.x[1]:.2f}")
    print(f"Minimum value of Z Objective Function: {res.fun:.2f}")
else:
    print("No solution found.")
