import { test, expect } from '@playwright/test';

test.describe('Story #200010919: User can view the TODO application screen', () => {
  test.beforeEach(async ({ page }) => {
    // 로컬 스토리지 초기화
    await page.goto('http://localhost:5173');
    await page.evaluate(() => localStorage.clear());
    await page.reload();
  });

  test('사용자는 TODO 애플리케이션을 사용하여 할 일을 관리할 수 있다', async ({ page }) => {
    // 1-4단계: 화면 요소 확인
    await expect(page.getByRole('heading', { name: 'TODO 리스트', level: 1 })).toBeVisible();
    await expect(page.getByRole('textbox')).toBeVisible();
    await expect(page.getByRole('button', { name: '추가' })).toBeVisible();

    // 빈 상태 메시지 확인
    await expect(page.getByText('할 일이 없습니다.')).toBeVisible();

    // 5-8단계: 첫 번째 TODO 항목 추가
    const inputBox = page.getByRole('textbox');
    const addButton = page.getByRole('button', { name: '추가' });

    await inputBox.fill('Buy groceries');
    await addButton.click();

    // 리스트에 항목이 추가되었는지 확인
    await expect(page.getByRole('listitem').filter({ hasText: 'Buy groceries' })).toBeVisible();

    // 입력 박스가 비워졌는지 확인
    await expect(inputBox).toHaveValue('');

    // 빈 상태 메시지가 사라졌는지 확인
    await expect(page.getByText('할 일이 없습니다.')).not.toBeVisible();

    // 9-12단계: 두 번째 TODO 항목 추가 및 순서 확인
    await inputBox.fill('Read a book');
    await addButton.click();

    // 두 항목이 모두 보이는지 확인
    await expect(page.getByRole('listitem').filter({ hasText: 'Read a book' })).toBeVisible();
    await expect(page.getByRole('listitem').filter({ hasText: 'Buy groceries' })).toBeVisible();

    // 항목 순서 확인 (Read a book이 최상단)
    const listItems = page.getByRole('listitem');
    await expect(listItems.nth(0)).toHaveText('Read a book');
    await expect(listItems.nth(1)).toHaveText('Buy groceries');

    // 13-14단계: 페이지 새로고침 후 데이터 유지 확인
    await page.reload();

    // 새로고침 후에도 두 항목이 올바른 순서로 유지되는지 확인
    await expect(page.getByRole('listitem').filter({ hasText: 'Read a book' })).toBeVisible();
    await expect(page.getByRole('listitem').filter({ hasText: 'Buy groceries' })).toBeVisible();

    const listItemsAfterReload = page.getByRole('listitem');
    await expect(listItemsAfterReload.nth(0)).toHaveText('Read a book');
    await expect(listItemsAfterReload.nth(1)).toHaveText('Buy groceries');
  });
});

