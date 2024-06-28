import { act, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import * as ItemRepository from './respository/ItemRepository';

import App from './App';

test('when search keyword then request one time after 500ms', () => {
  const spyItemRepository = jest.spyOn(ItemRepository, 'getItems').mockResolvedValue([]);
  render(<App />);

  jest.useFakeTimers();
  userEvent.type(screen.getByPlaceholderText('검색어'), 'Junhyunny');
  act(() => {
    jest.advanceTimersByTime(501);
  });

  expect(spyItemRepository).toHaveBeenCalledTimes(1);
  expect(spyItemRepository).toHaveBeenNthCalledWith(1, {
    keyword: 'Junhyunny',
  });
  expect(screen.getByText('현재 API 호출 횟수 = 1')).toBeInTheDocument();
});
