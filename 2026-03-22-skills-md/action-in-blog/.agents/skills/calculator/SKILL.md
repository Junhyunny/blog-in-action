---
name: calculator
description: 단순한 더하기 계산기. 사용자가 전달한 숫자들을 더한다. "더해줘", "더하기" 등의 명령어로 실행할 수 있다.
---

# Purpose (목적)

이 스킬은 사용자가 전달한 숫자들을 더하는 역할을 한다.

# Specification (명세)

현재 전달 받은 숫자들을 더하는 방법은 `${CLAUDE_SKILL_DIR}/scripts/add.py` 스크립트를 사용한다. 사용 예시는 다음과 같다.

```bash
python3 ${CLAUDE_SKILL_DIR}/scripts/add.py $ARGUMENTS[0] $ARGUMENTS[1]
```