import React, { useEffect, useState } from 'react';
import plusIcon from '../assets/icons/plus.svg';

interface Todo {
	id: number;
	text: string;
	completed: boolean;
}

const TodoPage: React.FC = () => {
	const [todos, setTodos] = useState<Todo[]>([]);
	const [inputValue, setInputValue] = useState<string>('');

	useEffect(() => {
		const savedTodos = localStorage.getItem('todos');
		if (savedTodos) {
			setTodos(JSON.parse(savedTodos));
		}
	}, []);

	const addTodo = () => {
		if (inputValue.trim() === '') {
			return;
		}

		const newTodo: Todo = {
			id       : Date.now(),
			text     : inputValue.trim(),
			completed: false
		};

		const newTodos = [...todos, newTodo];
		setTodos(newTodos);
		setInputValue('');
		localStorage.setItem('todos', JSON.stringify(newTodos));
	};

	const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
		if (e.key === 'Enter') {
			addTodo();
		}
	};

	return (
		<div className="bg-white content-stretch flex items-center justify-center relative size-full min-h-screen">
      <div className="content-stretch flex flex-col items-start max-w-[672px] relative shrink-0 w-[672px]">
        <div
					className="bg-white relative rounded-[14px] shrink-0 w-full shadow-[0px_10px_15px_-3px_rgba(0,0,0,0.1),0px_4px_6px_-4px_rgba(0,0,0,0.1)] border border-[rgba(0,0,0,0.1)]">
          <div
						className="box-border content-stretch flex flex-col gap-[24px] items-start overflow-clip p-px relative w-full">
            {/* Header */}
						<div
							className="box-border content-stretch flex flex-col items-start pb-[22px] pt-[24px] px-[24px] relative shrink-0 w-full">
              <div className="content-stretch flex items-center justify-between relative shrink-0 w-full">
                <div className="content-stretch flex flex-col items-center justify-center relative shrink-0">
                  <div
										className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3','Noto_Sans_KR:Light',sans-serif] justify-center leading-[0] relative shrink-0 text-[#1b1b1b] text-[18px] text-nowrap"
										style={{ fontVariationSettings: "'wght' 300" }}>
                    <p className="leading-[normal] whitespace-pre">TODO LIST</p>
                  </div>
                </div>
              </div>
            </div>

						{/* Content */}
						<div
							className="box-border content-stretch flex flex-col gap-[24px] items-start pb-[24px] pt-0 px-[24px] relative shrink-0 w-full">
              {/* Input Section */}
							<div className="content-stretch flex gap-[12px] items-start relative shrink-0 w-full">
                <div
									className="basis-0 bg-[#f3f3f5] grow h-[36px] min-h-px min-w-px overflow-clip relative rounded-[8px] shrink-0">
                  <input
										type="text"
										value={inputValue}
										onChange={(e) => setInputValue(e.target.value)}
										onKeyDown={handleKeyDown}
										placeholder="새로운 항목 입력"
										className="absolute inset-0 w-full h-full bg-transparent border-none outline-none px-[13px] py-[8.5px] font-['Hiragino_Kaku_Gothic_ProN:W3','Noto_Sans_KR:Light',sans-serif] text-[14px] font-light text-[#717182] placeholder-[#717182]"
									/>
                </div>
                <button
									onClick={addTodo}
									className="bg-[#030213] box-border content-stretch flex gap-[8px] h-[36px] items-center justify-center opacity-50 hover:opacity-100 transition-opacity pb-[8.5px] pt-[7.5px] px-[12px] relative rounded-[8px] shrink-0"
								>
                  <div
										className="box-border content-stretch flex flex-col h-[16px] items-start pl-0 pr-[8px] py-0 relative shrink-0 w-[24px]">
                    <div
											className="content-stretch flex flex-col items-center justify-center overflow-clip relative shrink-0 size-[16px]">
                      <div className="basis-0 grow min-h-px min-w-px relative shrink-0 w-[16px]">
					              <img alt="plusIcon" src={plusIcon} />
                      </div>
                    </div>
                  </div>
                  <span
										className="font-['Hiragino_Kaku_Gothic_ProN:W3','Noto_Sans_KR:Light',sans-serif] text-[14px] font-light text-center text-white leading-[20px]">
                    추가
                  </span>
                </button>
              </div>

							{todos.length > 0 && (
								<>
									<div className="bg-[rgba(0,0,0,0.1)] h-px shrink-0 w-full" />
									<div className="content-stretch flex flex-col gap-[12px] items-start relative shrink-0 w-full">
                    {todos.map((todo) => (
											<div
												key={todo.id}
												className="bg-white box-border content-stretch flex gap-[12px] items-center p-[17px] relative rounded-[10px] shrink-0 w-full border border-[rgba(0,0,0,0.1)] border-solid"
											>
												<div
													className="box-border content-stretch flex flex-col items-start pb-[5px] pt-[3px] px-0 relative shrink-0">
                          <div
														className="bg-[#f3f3f5] relative rounded-[4px] shrink-0 size-[16px] border border-[rgba(0,0,0,0.1)] border-solid shadow-[0px_1px_2px_0px_rgba(0,0,0,0.05)]" />
                        </div>
												<div
													className="basis-0 content-stretch flex flex-col grow items-start min-h-px min-w-px relative shrink-0">
                          <div className="content-stretch flex flex-col items-start relative shrink-0 w-full">
                            <p
															className="font-['Hiragino_Kaku_Gothic_ProN:W3','Noto_Sans_KR:Light',sans-serif] text-[16px] leading-[24px] w-full text-neutral-950 font-light">
                              {todo.text}
                            </p>
                          </div>
                        </div>
                      </div>
										))}
                  </div>
                </>
							)}
							<div className="h-[20px] shrink-0 w-full" />
            </div>
          </div>
        </div>
      </div>
    </div>
	);
};

export default TodoPage;

