import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import linprog

# Example Linear Programming Problem:
# Minimize Z = 3x + 8y
# Subject to the following constraints:
# x + y >= 8   -> -x - y <= -8
# 2x - 3y <= 0
# x + 2y <= 30
# 3x - y <= 0

c = [3, 8]


A = [[-1, -1],   # x + y >= 8 -> -x - y <= -8
     [2, -3],    # 2x - 3y <= 0
     [1, 2],     # x + 2y <= 30
     [3, -1]]    # 3x - y <= 0
b = [-8, 0, 30, 0]

# Variable bounds: 0 <= x <= 10, 0 <= y <= 9
bounds = [(0, 10), (0, 9)]

# Solve the LPP using scipy.optimize.linprog
result = linprog(c, A_ub=A, b_ub=b, bounds=bounds, method='highs')

# Display the result
if result.success:
    print("Optimal solution found!")
    print("Optimal value of Z:", result.fun)
    print("Optimal solution for x and y:", result.x)
else:
    print("No solution found.")

# Now, let's plot the feasible region and the solution

# Define x and y ranges for plotting
x_vals = np.linspace(0, 10, 400)
y_vals = np.linspace(0, 9, 400)
X, Y = np.meshgrid(x_vals, y_vals)

# Define each constraint as a line
plt.figure(figsize=(8, 8))

# Constraint 1: x + y >= 8 -> y >= 8 - x
plt.fill_between(x_vals, 8 - x_vals, 9, color='lightgrey',
                 alpha=0.5, label="x + y >= 8")

# Constraint 2: 2x - 3y <= 0 -> y >= 2x / 3
plt.fill_between(x_vals, 2 * x_vals / 3, 9, color='lightblue',
                 alpha=0.5, label="2x - 3y <= 0")

# Constraint 3: x + 2y <= 30 -> y <= (30 - x) / 2
plt.fill_between(x_vals, 0, (30 - x_vals) / 2,
                 color='lightgreen', alpha=0.5, label="x + 2y <= 30")

# Constraint 4: 3x - y <= 0 -> y >= 3x
plt.fill_between(x_vals, 3 * x_vals, 9, color='pink',
                 alpha=0.5, label="3x - y <= 0")

# Variable bounds
plt.xlim(0, 10)
plt.ylim(0, 9)
plt.xlabel('x')
plt.ylabel('y')
plt.title('Feasible Region and Optimal Solution')

# Plot the optimal solution
if result.success:
    plt.scatter(result.x[0], result.x[1], color='red',
                label="Optimal Solution", zorder=5)

plt.legend()
plt.grid(True)
plt.show()
