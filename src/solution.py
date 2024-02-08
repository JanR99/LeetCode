def getGoodIndices(variables, target):
    """
    :type variables: List[List[int]]
    :type target: int
    :rtype: List[int]
    """
    ans = []
    for i in range(len(variables)):
        v = variables[i]
        a, b, c, m = v[0], v[1], v[2], v[3]
        if (((a ** b) % 10) ** c) % m == target:
            ans.append(i)
    return ans


if __name__ == '__main__':
    pass
