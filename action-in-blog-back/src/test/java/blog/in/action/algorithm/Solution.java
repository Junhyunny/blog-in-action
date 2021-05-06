package blog.in.action.algorithm;

class Solution {

    public int solution(int n, int a, int b) {
        return Integer.toBinaryString((a - 1) ^ (b - 1)).length();
    }
}
