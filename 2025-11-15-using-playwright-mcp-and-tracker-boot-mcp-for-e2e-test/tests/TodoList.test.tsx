import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, beforeEach } from 'vitest';
import TodoList from '../src/components/TodoList';

describe('TodoList 컴포넌트', () => {
  beforeEach(() => {
    // 각 테스트 전에 로컬 스토리지 초기화
    localStorage.clear();
  });

  it('TODO 리스트 타이틀이 표시된다', () => {
    render(<TodoList />);

    const title = screen.getByText('TODO 리스트');
    expect(title).toBeInTheDocument();
  });

  it('입력 박스가 표시된다', () => {
    render(<TodoList />);

    const input = screen.getByRole('textbox');
    expect(input).toBeInTheDocument();
    expect(input).toHaveAttribute('type', 'text');
  });

  it('추가 버튼이 표시된다', () => {
    render(<TodoList />);

    const button = screen.getByRole('button', { name: '추가' });
    expect(button).toBeInTheDocument();
  });

  describe('TODO 항목 입력 및 추가 기능', () => {
    it('사용자가 TODO 항목을 입력하고 추가 버튼을 누르면 새로운 TODO 항목이 리스트에 추가된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 사용자가 TODO 항목을 입력
      await user.type(input, '새로운 할일');

      // 추가 버튼 클릭
      await user.click(addButton);

      // 새로운 TODO 항목이 리스트에 추가되었는지 확인
      const todoItem = screen.getByText('새로운 할일');
      expect(todoItem).toBeInTheDocument();
    });

    it('TODO 항목을 추가한 후 입력 박스의 내용이 지워진다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox') as HTMLInputElement;
      const addButton = screen.getByRole('button', { name: '추가' });

      // 사용자가 TODO 항목을 입력
      await user.type(input, '할일 입력 테스트');
      expect(input.value).toBe('할일 입력 테스트');

      // 추가 버튼 클릭
      await user.click(addButton);

      // 입력 박스가 비워졌는지 확인
      expect(input.value).toBe('');
    });

    it('새로운 TODO 항목은 리스트의 최상단에 추가된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 첫 번째 TODO 항목 추가
      await user.type(input, '첫 번째 할일');
      await user.click(addButton);

      // 두 번째 TODO 항목 추가
      await user.type(input, '두 번째 할일');
      await user.click(addButton);

      // TODO 리스트 항목들을 가져옴
      const todoItems = screen.getAllByTestId('todo-item');

      // 가장 최근에 추가된 항목이 첫 번째에 있는지 확인
      expect(todoItems[0]).toHaveTextContent('두 번째 할일');
      expect(todoItems[1]).toHaveTextContent('첫 번째 할일');
    });

    it('빈 문자열은 TODO 항목으로 추가되지 않는다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const addButton = screen.getByRole('button', { name: '추가' });

      // 빈 입력으로 추가 버튼 클릭
      await user.click(addButton);

      // TODO 리스트가 비어있는지 확인 (todo-item이 없어야 함)
      const todoItems = screen.queryAllByTestId('todo-item');
      expect(todoItems).toHaveLength(0);
    });

    it('공백만 있는 문자열은 TODO 항목으로 추가되지 않는다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 공백만 입력하고 추가 버튼 클릭
      await user.type(input, '   ');
      await user.click(addButton);

      // TODO 리스트가 비어있는지 확인
      const todoItems = screen.queryAllByTestId('todo-item');
      expect(todoItems).toHaveLength(0);

      // 입력 박스가 비워졌는지 확인
      const inputElement = screen.getByRole('textbox') as HTMLInputElement;
      expect(inputElement.value).toBe('');
    });
  });

  describe('TODO 리스트 렌더링 기능', () => {
    it('초기 상태에서는 빈 TODO 리스트가 표시된다', () => {
      render(<TodoList />);

      const todoItems = screen.queryAllByTestId('todo-item');
      expect(todoItems).toHaveLength(0);
    });

    it('TODO 리스트가 비어있을 때 빈 상태 메시지가 표시된다', () => {
      render(<TodoList />);

      const emptyMessage = screen.getByText('할 일이 없습니다.');
      expect(emptyMessage).toBeInTheDocument();
    });

    it('여러 개의 TODO 항목이 올바르게 렌더링된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 여러 TODO 항목 추가
      await user.type(input, '첫 번째 할일');
      await user.click(addButton);

      await user.type(input, '두 번째 할일');
      await user.click(addButton);

      await user.type(input, '세 번째 할일');
      await user.click(addButton);

      // 모든 항목이 렌더링되었는지 확인
      const todoItems = screen.getAllByTestId('todo-item');
      expect(todoItems).toHaveLength(3);

      // 각 항목의 내용 확인
      expect(todoItems[0]).toHaveTextContent('세 번째 할일');
      expect(todoItems[1]).toHaveTextContent('두 번째 할일');
      expect(todoItems[2]).toHaveTextContent('첫 번째 할일');
    });

    it('TODO 항목이 있을 때는 빈 상태 메시지가 표시되지 않는다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // TODO 항목 추가
      await user.type(input, '새로운 할일');
      await user.click(addButton);

      // 빈 상태 메시지가 없는지 확인
      const emptyMessage = screen.queryByText('할 일이 없습니다.');
      expect(emptyMessage).not.toBeInTheDocument();
    });

    it('TODO 리스트가 접근 가능한 구조로 렌더링된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // TODO 항목 추가
      await user.type(input, '접근성 테스트');
      await user.click(addButton);

      // TODO 리스트가 list 역할로 접근 가능한지 확인
      const todoList = screen.getByRole('list');
      expect(todoList).toBeInTheDocument();

      // TODO 항목이 listitem 역할로 접근 가능한지 확인
      const todoItem = screen.getByRole('listitem');
      expect(todoItem).toBeInTheDocument();
      expect(todoItem).toHaveTextContent('접근성 테스트');
    });

    it('긴 텍스트의 TODO 항목도 올바르게 렌더링된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });
      const longText = '이것은 매우 긴 TODO 항목 텍스트입니다. 이런 긴 텍스트도 올바르게 렌더링되고 표시되어야 합니다.';

      // 긴 텍스트 TODO 항목 추가
      await user.type(input, longText);
      await user.click(addButton);

      // 긴 텍스트가 올바르게 렌더링되었는지 확인
      const todoItem = screen.getByTestId('todo-item');
      expect(todoItem).toHaveTextContent(longText);
    });
  });

  describe('로컬 스토리지 연동 기능', () => {
    beforeEach(() => {
      // 각 테스트 전에 로컬 스토리지 초기화
      localStorage.clear();
    });

    it('페이지 로드 시 로컬 스토리지에서 기존 TODO 항목들을 복원한다', () => {
      // 로컬 스토리지에 미리 TODO 항목들을 저장
      const savedTodos = ['기존 할일 1', '기존 할일 2'];
      localStorage.setItem('todos', JSON.stringify(savedTodos));

      render(<TodoList />);

      // 저장된 TODO 항목들이 화면에 표시되는지 확인
      expect(screen.getByText('기존 할일 1')).toBeInTheDocument();
      expect(screen.getByText('기존 할일 2')).toBeInTheDocument();
    });

    it('새로운 TODO 항목을 추가하면 로컬 스토리지에 자동으로 저장된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // TODO 항목 추가
      await user.type(input, '새로운 할일');
      await user.click(addButton);

      // 로컬 스토리지에 저장되었는지 확인
      const savedTodos = JSON.parse(localStorage.getItem('todos') || '[]');
      expect(savedTodos).toContain('새로운 할일');
    });

    it('여러 TODO 항목을 추가하면 모두 로컬 스토리지에 저장된다', async () => {
      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 여러 TODO 항목 추가
      await user.type(input, '첫 번째 할일');
      await user.click(addButton);

      await user.type(input, '두 번째 할일');
      await user.click(addButton);

      await user.type(input, '세 번째 할일');
      await user.click(addButton);

      // 로컬 스토리지에 모든 항목이 올바른 순서로 저장되었는지 확인
      const savedTodos = JSON.parse(localStorage.getItem('todos') || '[]');
      expect(savedTodos).toHaveLength(3);
      expect(savedTodos[0]).toBe('세 번째 할일');
      expect(savedTodos[1]).toBe('두 번째 할일');
      expect(savedTodos[2]).toBe('첫 번째 할일');
    });

    it('로컬 스토리지가 비어있으면 빈 TODO 리스트로 시작한다', () => {
      // 로컬 스토리지가 비어있는 상태에서 렌더링
      render(<TodoList />);

      // 빈 상태 메시지가 표시되는지 확인
      expect(screen.getByText('할 일이 없습니다.')).toBeInTheDocument();

      // TODO 항목이 없는지 확인
      const todoItems = screen.queryAllByTestId('todo-item');
      expect(todoItems).toHaveLength(0);
    });

    it('잘못된 형식의 로컬 스토리지 데이터가 있어도 오류 없이 처리한다', () => {
      // 잘못된 JSON 데이터 저장
      localStorage.setItem('todos', 'invalid json');

      // 오류 없이 렌더링되고 빈 상태로 시작하는지 확인
      expect(() => render(<TodoList />)).not.toThrow();
      expect(screen.getByText('할 일이 없습니다.')).toBeInTheDocument();
    });

    it('로컬 스토리지에 null이 저장되어 있어도 정상 동작한다', () => {
      localStorage.setItem('todos', 'null');

      render(<TodoList />);

      // 빈 상태로 정상 동작하는지 확인
      expect(screen.getByText('할 일이 없습니다.')).toBeInTheDocument();
    });

    it('기존 TODO 항목이 있는 상태에서 새 항목을 추가하면 기존 항목과 함께 저장된다', async () => {
      // 기존 TODO 항목 설정
      const existingTodos = ['기존 할일'];
      localStorage.setItem('todos', JSON.stringify(existingTodos));

      const user = userEvent.setup();
      render(<TodoList />);

      const input = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: '추가' });

      // 새 TODO 항목 추가
      await user.type(input, '새 할일');
      await user.click(addButton);

      // 로컬 스토리지에 기존 항목과 새 항목이 모두 저장되었는지 확인
      const savedTodos = JSON.parse(localStorage.getItem('todos') || '[]');
      expect(savedTodos).toHaveLength(2);
      expect(savedTodos).toContain('기존 할일');
      expect(savedTodos).toContain('새 할일');
      expect(savedTodos[0]).toBe('새 할일'); // 새 항목이 최상단
    });
  });
});
