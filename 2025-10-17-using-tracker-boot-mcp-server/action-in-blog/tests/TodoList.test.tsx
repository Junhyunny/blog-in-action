import { render, screen } from '@testing-library/react';
import { describe, it, expect } from 'vitest';
import TodoList from '../src/components/TodoList';

describe('TodoList 컴포넌트', () => {
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
});
