package blog.in.action.algorithm;

    class Solution {
    
        public void restore(int[] lost, int index, int beforeValue) {
            // 탐색을 마친 후 데이터 값 복구
            if (index < lost.length && lost[index] == -1) {
                lost[index] = beforeValue;
            }
        }
    
        // 빌리는데 성공한 학생 수
        public int reserve(int[] lost, int[] reserve, int curIndex, int canReserveNumer) {
            int index = 0;
            int temp = 0;
            int result = 0;
            int length = lost.length;
            for (index = 0; index < length; index++) {
                if (canReserveNumer == lost[index]) {
                    temp = lost[index];
                    lost[index] = -1;
                    result = 1;
                    break;
                }
            }
            // 마지막 체크할 학생 수 초과
            if (curIndex == reserve.length) {
                restore(lost, index, temp);
                return result;
            }
            int minusCount = reserve(lost, reserve, curIndex + 1, reserve[curIndex] - 1);
            int plusCount = reserve(lost, reserve, curIndex + 1, reserve[curIndex] + 1);
            // 탐색을 마친 후 데이터 값 복구
            restore(lost, index, temp);
            return (minusCount > plusCount ? minusCount : plusCount) + result;
        }
    
        public int solution(int n, int[] lost, int[] reserve) {
            // 여별의 옷이 있는 학생이 도두맞은 케이스 제거
            int lostLength = lost.length;
            int reserveLength = reserve.length;
            for (int index = 0; index < reserveLength; index++) {
                for (int subIndex = 0; subIndex < lostLength; subIndex++) {
                    if (lost[subIndex] == reserve[index]) {
                        lost[subIndex] = -1;
                        reserve[index] = -2;
                        break;
                    }
                }
            }
            int minusCount = reserve(lost, reserve, 1, reserve[0] - 1);
            int plusCount = reserve(lost, reserve, 1, reserve[0] + 1);
            int result = minusCount > plusCount ? minusCount : plusCount;
            int answer = n - lost.length + result;
            return answer;
        }
    }