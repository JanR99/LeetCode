def reverse_seq(n):
    return list(range(n, 0, -1))


def greet(name):
    if name == "Johnny":
        return "Hello, my love!"
    return "Hello, {name}!".format(name=name)


def array_plus_array(arr1, arr2):
    return sum(arr1) + sum(arr2)


