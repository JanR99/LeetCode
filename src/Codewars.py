def reverse_seq(n):
    return list(range(n, 0, -1))


def greet(name):
    if name == "Johnny":
        return "Hello, my love!"
    return "Hello, {name}!".format(name=name)


def array_plus_array(arr1, arr2):
    return sum(arr1) + sum(arr2)


def pillars(num_pill, dist, width):
    if num_pill == 1:
        return 0
    ans = 0
    if num_pill > 2:
        ans += int(((num_pill - 2) * width))
    return int(ans + (num_pill - 1) * dist * 100)


def find(a, e):
    return a.index(e) if e in a else "Not found"


def find_average(numbers: list):
    return sum(numbers) / len(numbers) if len(numbers) > 0 else 0


if __name__ == '__main__':
    print(find([1, 2, 3], 4))
