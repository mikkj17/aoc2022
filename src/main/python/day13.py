from __future__ import annotations

import functools


test = '''\
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
'''.strip()


def compare_int(left: int, right: int) -> int:
    return -1 if left < right else 0 if left == right else 1


def compare(left: int|list, right: int|list, *, depth: int) -> int:
    if both_type(left, right, list):
        try:
            for l, r in zip(left, right, strict=True):
                if both_type(l, r, int):
                    comparison = compare_int(l, r)
                else:
                    comparison = compare(l, r, depth=depth+1)

                if comparison != 0:
                    return comparison

        except ValueError:
            return -1 if len(left) < len(right) else 1

        return -1 if depth == 0 else 0

    else:
        if isinstance(left, int):
            return compare([left], right, depth=depth+1)
        else:
            return compare(left, [right], depth=depth+1)


def both_type(left: int|list, right: int|list, type: object) -> bool:
    return isinstance(left, type) and isinstance(right, type)


def part_one(inp: str) -> int:
    packet_pairs = [tuple(map(eval, pair.splitlines())) for pair in inp.split("\n\n")]
    return sum(
        i for i, pair in enumerate(packet_pairs, start=1)
        if compare(*pair, depth=0) == -1
    )


def part_two(inp: str) -> int:
    dividers = [[[2]], [[6]]]
    packets = [eval(line) for line in inp.splitlines() if line != ''] + dividers
    comparator = functools.partial(compare, depth=0)
    packets.sort(key=functools.cmp_to_key(comparator))
    first, second = [packets.index(divider) + 1 for divider in dividers]
    return first * second


def main() -> None:
    with open('../resources/day13.txt') as f:
        inp = f.read().strip()
    print(part_one(inp))
    print(part_two(inp))


if __name__ == '__main__':
    main()
