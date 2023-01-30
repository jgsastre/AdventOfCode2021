# Python3 code to Check for 
# balanced parentheses in an expression
open_list = ["[","{","(", "<"]
close_list = ["]","}",")", ">"]
  
# Function to check parentheses
def check(myStr):
    stack = []
    for i in myStr:
        if i in open_list:
            stack.append(i)
        elif i in close_list:
            pos = close_list.index(i)
            if ((len(stack) > 0) and
                (open_list[pos] == stack[len(stack)-1])):
                stack.pop()
            else:
                return "Unbalanced: " + i
    if len(stack) == 0:
        return "Balanced"
    else:
        return "Invalid"
  
if __name__ == "__main__":
    file1 = open('input.txt', 'r')
    lines = file1.readlines()
    for i, val in enumerate(lines):
        result = check(val)
        print(f"{i}: {result}")
