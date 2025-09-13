import { render, screen } from '@testing-library/react';
import { describe, it, expect } from 'vitest';
import TodoApp from '../src/TodoApp';

describe('TodoApp', () => {
  it('TODO List 타이틀이 표시된다', () => {
    render(<TodoApp />);
    
    const title = screen.getByText('TODO List');
    expect(title).toBeInTheDocument();
  });

  it('입력 창이 표시된다', () => {
    render(<TodoApp />);
    
    const input = screen.getByRole('textbox');
    expect(input).toBeInTheDocument();
  });

  it('추가 버튼이 표시된다', () => {
    render(<TodoApp />);
    
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

    render(<TodoApp />);
    
    expect(screen.getByText('첫 번째 할 일')).toBeInTheDocument();
    expect(screen.getByText('두 번째 할 일')).toBeInTheDocument();
    
    // 테스트 후 localStorage 정리
    localStorage.clear();
  });
});