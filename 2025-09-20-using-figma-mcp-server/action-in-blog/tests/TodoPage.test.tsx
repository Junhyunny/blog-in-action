import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import TodoPage from '../src/pages/TodoPage.tsx';

describe('TodoPage', () => {
  beforeEach(() => {
    // 각 테스트 전에 localStorage 정리
    localStorage.clear();
  });

  it('TODO List 타이틀이 표시된다', () => {
    render(<TodoPage />);
    
    const title = screen.getByText('TODO 리스트');
    expect(title).toBeInTheDocument();
  });

  it('입력 창이 표시된다', () => {
    render(<TodoPage />);
    
    const input = screen.getByRole('textbox');
    expect(input).toBeInTheDocument();
  });

  it('추가 버튼이 표시된다', () => {
    render(<TodoPage />);
    
    const addButton = screen.getByRole('button', { name: '추가' });
    expect(addButton).toBeInTheDocument();
  });

  it('기존 TODO 리스트가 표시된다', () => {
    // localStorage에 기존 TODO 항목들 설정
    const mockTodos = [
      { id: 1, text: '첫 번째 할 일', completed: false },
      { id: 2, text: '두 번째 할 일', completed: true }
    ];
    localStorage.setItem('todos', JSON.stringify(mockTodos));

    render(<TodoPage />);

    expect(screen.getByText('첫 번째 할 일')).toBeInTheDocument();
    expect(screen.getByText('두 번째 할 일')).toBeInTheDocument();
  });

  describe('새로운 태스크 추가', () => {
    it('사용자가 텍스트 박스에 태스크를 입력하고 추가 버튼을 누르면 태스크가 리스트에 추가되고 입력란이 초기화된다', async () => {
      // Given: TODO 애플리케이션에 접속
      const user = userEvent.setup();
      render(<TodoPage />);

      const textInput = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: /추가/i });

      // When: 텍스트 박스에 새로운 태스크를 입력 후 추가 버튼을 누름
      const newTask = '새로운 할 일';
      await user.type(textInput, newTask);
      await user.click(addButton);

      // Then: 새롭게 작성한 태스크가 아래 리스트에 추가됨
      expect(screen.getByText(newTask)).toBeInTheDocument();

      // Then: 텍스트 박스에 입력한 정보가 사라짐
      expect(textInput).toHaveValue('');
    });

    it('추가된 태스크가 localStorage에 저장되어 새로고침 후에도 유지된다', async () => {
      // Given: TODO 애플리케이션에 접속
      vi.setSystemTime(1758330801465)
      const user = userEvent.setup();
      const { unmount } = render(<TodoPage />);

      const textInput = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: /추가/i });

      // When: 새로운 태스크를 추가
      const newTask = '지속되는 할 일';
      await user.type(textInput, newTask);
      await user.click(addButton);

      // 컴포넌트 언마운트 (새로고침 시뮬레이션)
      unmount();

      // Then: 새로고침 후 다시 렌더링해도 태스크가 유지됨
      render(<TodoPage />);
      expect(screen.getByText(newTask)).toBeInTheDocument();
      expect(JSON.parse(localStorage.getItem('todos')!)).toEqual([{id: 1758330801465, text: '지속되는 할 일', completed: false}]);
    });

    it('빈 텍스트로는 태스크를 추가할 수 없다', async () => {
      // Given: TODO 애플리케이션에 접속
      const user = userEvent.setup();
      render(<TodoPage />);

      const textInput = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: /추가/i });

      // When: 빈 텍스트로 추가 버튼을 누름
      await user.clear(textInput);
      await user.click(addButton);

      // Then: 태스크가 추가되지 않음
      const savedTodos = localStorage.getItem('todos');
      if (savedTodos) {
        const todos = JSON.parse(savedTodos);
        expect(todos).toHaveLength(0);
      } else {
        expect(savedTodos).toBeNull();
      }
    });

    it('공백만 입력된 텍스트로는 태스크를 추가할 수 없다', async () => {
      // Given: TODO 애플리케이션에 접속
      const user = userEvent.setup();
      render(<TodoPage />);

      const textInput = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: /추가/i });

      // When: 공백만 입력하고 추가 버튼을 누름
      await user.type(textInput, '   ');
      await user.click(addButton);

      // Then: 태스크가 추가되지 않음
      const savedTodos = localStorage.getItem('todos');
      if (savedTodos) {
        const todos = JSON.parse(savedTodos);
        expect(todos).toHaveLength(0);
      } else {
        expect(savedTodos).toBeNull();
      }
    });

    it('여러 개의 태스크를 순차적으로 추가할 수 있다', async () => {
      // Given: TODO 애플리케이션에 접속
      const user = userEvent.setup();
      render(<TodoPage />);

      const textInput = screen.getByRole('textbox');
      const addButton = screen.getByRole('button', { name: /추가/i });

      // When: 첫 번째 태스크 추가
      const firstTask = '첫 번째 할 일';
      await user.type(textInput, firstTask);
      await user.click(addButton);

      // When: 두 번째 태스크 추가
      const secondTask = '두 번째 할 일';
      await user.type(textInput, secondTask);
      await user.click(addButton);

      // Then: 두 태스크 모두 리스트에 표시됨
      expect(screen.getByText(firstTask)).toBeInTheDocument();
      expect(screen.getByText(secondTask)).toBeInTheDocument();

      // Then: localStorage에도 두 태스크가 모두 저장됨
      const savedTodos = localStorage.getItem('todos');
      expect(savedTodos).not.toBeNull();
      const todos = JSON.parse(savedTodos!);
      expect(todos).toHaveLength(2);
      expect(todos.some((todo: { text: string }) => todo.text === firstTask)).toBe(true);
      expect(todos.some((todo: { text: string }) => todo.text === secondTask)).toBe(true);
    });

    it('Enter 키를 눌러서도 태스크를 추가할 수 있다', async () => {
      // Given: TODO 애플리케이션에 접속
      const user = userEvent.setup();
      render(<TodoPage />);

      const textInput = screen.getByRole('textbox');

      // When: 텍스트 박스에 태스크를 입력하고 Enter 키를 누름
      const newTask = 'Enter로 추가한 할 일';
      await user.type(textInput, newTask);
      await user.keyboard('{Enter}');

      // Then: 태스크가 리스트에 추가됨
      expect(screen.getByText(newTask)).toBeInTheDocument();

      // Then: 텍스트 박스가 초기화됨
      expect(textInput).toHaveValue('');
    });
  });
});