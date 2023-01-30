

def longestString(s):
    first = 0
    viewed = dict()
    max_stream_length = 0
    i = 0
    while i < len(s):
        c = s[i]
        last_index = viewed.get(c)

        if last_index is not None:
            stream_length = i - first
            if stream_length > max_stream_length:
                max_stream_length = stream_length
            first = i = last_index + 1
            viewed.clear()
        else:
            viewed[c] = i
            i = i + 1

    max_candidate = len(s) - first
    if max_candidate > max_stream_length:
        max_stream_length = max_candidate

    return max_stream_length


longestString("abcabcbb")
longestString("bbbbb")
longestString("pwwkew")
longestString(" ")
longestString("ohvhjdml")
dir(set()) # ['__class__', '__contains__', '__delattr__', '__delitem__', '__dir__', '__doc__', '__eq__', '__format__', '__ge__', '__getattribute__', '__getitem__', '__gt__', '__hash__', '__init__', '__init_subclass__', '__iter__', '__le__', '__len__', '__lt__', '__ne__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__reversed__', '__setattr__', '__setitem__', '__sizeof__', '__str__', '__subclasshook__', 'clear', 'copy', 'fromkeys', 'get', 'items', 'keys', 'pop', 'popitem', 'setdefault', 'update', 'values']
# NameError: name 'doc' is not defined
# ['__and__', '__class__', '__contains__', '__delattr__', '__dir__', '__doc__', '__eq__', '__format__', '__ge__', '__getattribute__', '__gt__', '__hash__', '__iand__', '__init__', '__init_subclass__', '__ior__', '__isub__', '__iter__', '__ixor__', '__le__', '__len__', '__lt__', '__ne__', '__new__', '__or__', '__rand__', '__reduce__', '__reduce_ex__', '__repr__', '__ror__', '__rsub__', '__rxor__', '__setattr__', '__sizeof__', '__str__', '__sub__', '__subclasshook__', '__xor__', 'add', 'clear', 'copy', 'difference', 'difference_update', 'discard', 'intersection', 'intersection_update', 'isdisjoint', 'issubset', 'issuperset', 'pop', 'remove', 'symmetric_difference', 'symmetric_difference_update', 'union', 'update']

longestString("aa")
print("foo")

if {1: "foo"}.get(2):
    "foo"

"aa"[0:1]

a = Stack()
a = [1, 2, 3]
b = [4, 5]
a + b
c = set()

c.add(3)
c


def unique_names(names1, names2):
    names = set()
    for name in (names1 + names2):
        names.add(name)
    return names

if __name__ == "__main__":
    names1 = ["Ava", "Emma", "Olivia"]
    names2 = ["Olivia", "Sophia", "Emma"]
    print(unique_names(names1, names2)) # should print Ava, Emma, Olivia, Sophia

list(set())
unique_names([],[])


class Solution(object):
    def longestCommonPrefix(self, strs):
        """
        :type strs: List[str]
        :rtype: str
        """
        index = 0
        equal = True
        while equal:
            for item, item2 in zip(strs, strs[1:]):
                if index == len(item) or index == len(item2):
                    equal = False
                    break
                equal = equal and item[index] == item2[index]
                if not equal:
                    break
            if equal:
                index = index + 1
        return strs[0][0:index]


a = Solution()
a.longestCommonPrefix(["foo", "fooh"])

from collections import deque

class Solution(object):
    mapping = {'(': ')', '{': '}', '[': ']'}
    valid_closing = set(mapping.values())

    def isValid(self, s):
        """
        :type s: str
        :rtype: bool
        """
        valid = True
        opened = deque()
        for c in s:
            closing = self.mapping.get(c)
            if closing:
                opened.append(closing)
            else:
                if len(opened) == 0 or c not in valid_closing:
                    valid = False
                    break

                expected_closing = opened.pop()
                valid = expected_closing == c
                if not valid:
                    break
        return valid and len(opened) == 0


{1: 2, 3: 4}.values()
                

Solution().isValid("]")
a
dir(a) # ['__add__', '__bool__', '__class__', '__contains__', '__copy__', '__delattr__', '__delitem__', '__dir__', '__doc__', '__eq__', '__format__', '__ge__', '__getattribute__', '__getitem__', '__gt__', '__hash__', '__iadd__', '__imul__', '__init__', '__init_subclass__', '__iter__', '__le__', '__len__', '__lt__', '__mul__', '__ne__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__reversed__', '__rmul__', '__setattr__', '__setitem__', '__sizeof__', '__str__', '__subclasshook__', 'append', 'appendleft', 'clear', 'copy', 'count', 'extend', 'extendleft', 'index', 'insert', 'maxlen', 'pop', 'popleft', 'remove', 'reverse', 'rotate']


# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution(object):
    def mergeTwoLists(self, list1, list2):
        """
        :type list1: Optional[ListNode]
        :type list2: Optional[ListNode]
        :rtype: Optional[ListNode]
        """

        def sort(node1, node2, merged):
            if not node1 and not node2:
                return merged
            elif node1 and not node2:
                node = ListNode(node1.val)
                merged.next = node
                sort(node1.next, node2, node)
            elif node2 and not node1:
                node = ListNode(node2.val)
                merged.next = node
                sort(node1, node2.next, node)
            else:
                if node1.val < node2.val:
                    node = ListNode(node1.val)
                    merged.next = node
                    sort(node1.next, node2, node) 
                else:
                    node = ListNode(node2.val)
                    merged.next = node
                    sort(node1, node2.next, node)

        pre_result = ListNode()
        sort(list1, list2, pre_result)
        return pre_result.next


class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """

        neg = x < 0
        if neg:
            x = -x

        result = 0
        while x > 0:
            rem = x % 10
            x = (x - rem)/10
            result = 10*result + rem

        if neg:
            result = -result

        if result > 2**31 - 1 or result < -2**31:
            return 0

        return int(result)

dir(int) # <class 'int'>
# ['__abs__', '__add__', '__and__', '__bool__', '__ceil__', '__class__', '__delattr__', '__dir__', '__divmod__', '__doc__', '__eq__', '__float__', '__floor__', '__floordiv__', '__format__', '__ge__', '__getattribute__', '__getnewargs__', '__gt__', '__hash__', '__index__', '__init__', '__init_subclass__', '__int__', '__invert__', '__le__', '__lshift__', '__lt__', '__mod__', '__mul__', '__ne__', '__neg__', '__new__', '__or__', '__pos__', '__pow__', '__radd__', '__rand__', '__rdivmod__', '__reduce__', '__reduce_ex__', '__repr__', '__rfloordiv__', '__rlshift__', '__rmod__', '__rmul__', '__ror__', '__round__', '__rpow__', '__rrshift__', '__rshift__', '__rsub__', '__rtruediv__', '__rxor__', '__setattr__', '__sizeof__', '__str__', '__sub__', '__subclasshook__', '__truediv__', '__trunc__', '__xor__', 'as_integer_ratio', 'bit_length', 'conjugate', 'denominator', 'from_bytes', 'imag', 'numerator', 'real', 'to_bytes']
Solution().reverse(1534236469)
             
b = "PAYPALISHIRING"
a = [i for i in 1:4]
c = [b[4*i] for i in 1:100]







