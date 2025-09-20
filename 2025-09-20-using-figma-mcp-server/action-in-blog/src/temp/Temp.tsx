import plusIcon from '../assets/icons/plus.svg';
import checkIcon from '../assets/icons/check.svg';
import editIcon from '../assets/icons/edit.svg';
import trashIcon from '../assets/icons/trash.svg';
import saveIcon from '../assets/icons/save.svg';
import xIcon from '../assets/icons/x.svg';

interface Component1Props {
  variant?: "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8";
}

function Component1({ variant = "1" }: Component1Props) {
  if (variant === "2") {
    return (
      <div className="relative size-full" data-name="variant=2" data-node-id="1:8">
        <div className="absolute inset-[8.33%_8.33%_8.34%_8.33%]" data-name="Vector" data-node-id="1:9">
          <div className="absolute inset-[-5%]">
            <img alt="편집" className="block max-w-none size-full" src={editIcon} />
          </div>
        </div>
      </div>
    );
  }
  if (variant === "3") {
    return (
      <div className="relative size-full" data-name="variant=3" data-node-id="1:10">
        <img alt="삭제" className="block max-w-none size-full" src={trashIcon} />
      </div>
    );
  }
  if (variant === "4") {
    return (
      <div className="relative size-full" data-name="variant=4" data-node-id="1:16">
        <div className="absolute bottom-[29.17%] left-[16.67%] right-[16.67%] top-1/4" data-name="Vector" data-node-id="1:17">
          <div className="absolute inset-[-9.09%_-6.25%]">
            <img alt="체크" className="block max-w-none size-full" src={checkIcon} />
          </div>
        </div>
      </div>
    );
  }
  if (variant === "5") {
    return (
      <div className="relative size-full" data-name="variant=5" data-node-id="1:18">
        <div className="absolute inset-[8.33%_8.33%_8.34%_8.33%]" data-name="Vector" data-node-id="1:19">
          <div className="absolute inset-[-5%]">
            <img alt="편집" className="block max-w-none size-full" src={editIcon} />
          </div>
        </div>
      </div>
    );
  }
  if (variant === "6") {
    return (
      <div className="relative size-full" data-name="variant=6" data-node-id="1:20">
        <img alt="삭제" className="block max-w-none size-full" src={trashIcon} />
      </div>
    );
  }
  if (variant === "7") {
    return (
      <div className="relative size-full" data-name="variant=7" data-node-id="1:26">
        <div className="absolute bottom-[29.17%] left-[16.67%] right-[16.67%] top-1/4" data-name="Vector" data-node-id="1:27">
          <div className="absolute inset-[-9.09%_-6.25%]">
            <img alt="저장" className="block max-w-none size-full" src={saveIcon} />
          </div>
        </div>
      </div>
    );
  }
  if (variant === "8") {
    return (
      <div className="relative size-full" data-name="variant=8" data-node-id="1:28">
        <div className="absolute inset-1/4" data-name="Vector" data-node-id="1:29">
          <div className="absolute inset-[-8.33%]">
            <img alt="취소" className="block max-w-none size-full" src={xIcon} />
          </div>
        </div>
      </div>
    );
  }
  return (
    <div className="relative size-full" data-name="variant=1" data-node-id="1:5">
      <div className="absolute bottom-1/2 left-[20.83%] right-[20.83%] top-1/2" data-name="Vector" data-node-id="1:6">
        <div className="absolute inset-[-0.67px_-7.14%]">
          <img alt="추가" className="block max-w-none size-full" src={plusIcon} />
        </div>
      </div>
    </div>
  );
}

export default function Temp() {
  return (
    <div className="bg-white content-stretch flex items-center justify-center relative size-full" data-name="Container" data-node-id="1:31">
      <div className="content-stretch flex flex-col items-start max-w-[672px] relative shrink-0 w-[672px]" data-name="Card" data-node-id="1:32">
        <div className="bg-white relative rounded-[14px] shrink-0 w-full" data-name="Background+Border+Shadow" data-node-id="1:33">
          <div className="box-border content-stretch flex flex-col gap-[24px] items-start overflow-clip p-px relative w-full">
            <div className="box-border content-stretch flex flex-col items-start pb-[22px] pt-[24px] px-[24px] relative shrink-0 w-full" data-name="CardHeader" data-node-id="1:34">
              <div className="content-stretch flex items-center justify-between relative shrink-0 w-full" data-name="Heading 4" data-node-id="1:35">
                <div className="content-stretch flex flex-col items-center justify-center relative shrink-0" data-name="Container" data-node-id="1:36">
                  <div className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3',_'Noto_Sans_KR:Light',_sans-serif] justify-center leading-[0] relative shrink-0 text-[#1b1b1b] text-[18px] text-nowrap" data-node-id="1:127" style={{ fontVariationSettings: "'wght' 300" }}>
                    <p className="leading-[normal] whitespace-pre">TODO 애플리케이션</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="box-border content-stretch flex flex-col gap-[24px] items-start pb-[24px] pt-0 px-[24px] relative shrink-0 w-full" data-name="CardContent" data-node-id="1:40">
              <div className="content-stretch flex gap-[12px] items-start relative shrink-0 w-full" data-name="Input" data-node-id="1:41">
                <div className="basis-0 bg-[#f3f3f5] grow h-[36px] min-h-px min-w-px overflow-clip relative rounded-[8px] shrink-0" data-name="Input" data-node-id="1:42">
                  <div className="absolute bottom-[6.5px] box-border content-stretch flex flex-col items-start left-[13px] overflow-clip pb-[3px] pl-0 pr-[361.97px] pt-px top-[8.5px]" data-name="Container" data-node-id="1:43">
                    <div className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3',_'Noto_Sans_KR:Light',_sans-serif] justify-center leading-[0] relative shrink-0 text-[#717182] text-[14px] text-nowrap" data-node-id="1:44" style={{ fontVariationSettings: "'wght' 300" }}>
                      <p className="leading-[normal] whitespace-pre">새로운 항목 입력</p>
                    </div>
                  </div>
                  <div className="absolute bottom-[9.5px] left-[13px] top-[9.5px] w-[500px]" data-name="Container" data-node-id="1:45" />
                </div>
                <div className="bg-[#030213] box-border content-stretch flex gap-[8px] h-[36px] items-center justify-center opacity-50 pb-[8.5px] pt-[7.5px] px-[12px] relative rounded-[8px] shrink-0" data-name="Button" data-node-id="1:46">
                  <div className="box-border content-stretch flex flex-col h-[16px] items-start pl-0 pr-[8px] py-0 relative shrink-0 w-[24px]" data-name="SVG:margin" data-node-id="1:47">
                    <div className="content-stretch flex flex-col items-center justify-center overflow-clip relative shrink-0 size-[16px]" data-name="SVG" data-node-id="1:48">
                      <div className="basis-0 grow min-h-px min-w-px relative shrink-0 w-[16px]" data-name="Component 1" data-node-id="1:49">
                        <Component1 variant="1" />
                      </div>
                    </div>
                  </div>
                  <div className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3',_'Noto_Sans_KR:Light',_sans-serif] justify-center leading-[0] relative shrink-0 text-[14px] text-center text-nowrap text-white" data-node-id="1:50" style={{ fontVariationSettings: "'wght' 300" }}>
                    <p className="leading-[20px] whitespace-pre">추가</p>
                  </div>
                </div>
              </div>
              <div className="bg-[rgba(0,0,0,0.1)] h-px shrink-0 w-full" data-name="Horizontal Divider" data-node-id="1:51" />
              <div className="content-stretch flex flex-col gap-[12px] items-start relative shrink-0 w-full" data-name="Card" data-node-id="1:52">
                <div className="bg-white box-border content-stretch flex gap-[12px] items-center p-[17px] relative rounded-[10px] shrink-0 w-full" data-name="TaskDefault" data-node-id="1:53">
                  <div aria-hidden="true" className="absolute border border-[rgba(0,0,0,0.1)] border-solid inset-0 pointer-events-none rounded-[10px]" />
                  <div className="box-border content-stretch flex flex-col items-start pb-[5px] pt-[3px] px-0 relative shrink-0" data-name="Container" data-node-id="1:54">
                    <div className="bg-[#f3f3f5] relative rounded-[4px] shrink-0 size-[16px]" data-name="Checkbox" data-node-id="1:55">
                      <div aria-hidden="true" className="absolute border border-[rgba(0,0,0,0.1)] border-solid inset-0 pointer-events-none rounded-[4px] shadow-[0px_1px_2px_0px_rgba(0,0,0,0.05)]" />
                    </div>
                  </div>
                  <div className="basis-0 content-stretch flex flex-col grow items-start min-h-px min-w-px relative shrink-0" data-name="Container" data-node-id="1:56">
                    <div className="content-stretch flex flex-col items-start relative shrink-0 w-full" data-name="Container" data-node-id="1:57">
                      <div className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3',_'Noto_Sans_KR:Light',_sans-serif] justify-center leading-[0] relative shrink-0 text-[16px] text-neutral-950 w-full" data-node-id="1:58" style={{ fontVariationSettings: "'wght' 300" }}>
                        <p className="leading-[24px]">테스트</p>
                      </div>
                    </div>
                  </div>
                  <div className="content-stretch flex gap-[4px] items-start relative shrink-0" data-name="Container" data-node-id="1:59">
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:60">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:61">
                        <Component1 variant="2" />
                      </div>
                    </div>
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:62">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:63">
                        <Component1 variant="3" />
                      </div>
                    </div>
                  </div>
                </div>
                <div className="bg-[rgba(236,236,240,0.5)] box-border content-stretch flex gap-[12px] items-center p-[17px] relative rounded-[10px] shrink-0 w-full" data-name="TaskDone" data-node-id="1:64">
                  <div aria-hidden="true" className="absolute border border-[#ececf0] border-solid inset-0 pointer-events-none rounded-[10px]" />
                  <div className="box-border content-stretch flex flex-col items-start pb-[5px] pt-[3px] px-0 relative shrink-0" data-name="Container" data-node-id="1:65">
                    <div className="bg-[#030213] box-border content-stretch flex items-start justify-center p-px relative rounded-[4px] shrink-0 size-[16px]" data-name="Checkbox" data-node-id="1:66">
                      <div aria-hidden="true" className="absolute border border-[#030213] border-solid inset-0 pointer-events-none rounded-[4px] shadow-[0px_1px_2px_0px_rgba(0,0,0,0.05)]" />
                      <div className="basis-0 content-stretch flex grow items-center justify-center min-h-px min-w-px relative shrink-0" data-name="Container" data-node-id="1:67">
                        <div className="relative shrink-0 size-[14px]" data-name="Component 1" data-node-id="1:68">
                          <Component1 variant="4" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="basis-0 content-stretch flex flex-col grow items-start min-h-px min-w-px relative shrink-0" data-name="Container" data-node-id="1:69">
                    <div className="content-stretch flex flex-col items-start relative shrink-0 w-full" data-name="Container" data-node-id="1:70">
                      <div className="flex flex-col font-['SF_Pro_Text:Regular',_sans-serif] justify-center leading-[0] not-italic relative shrink-0 text-[#717182] text-[16px] w-full" data-node-id="1:71">
                        <p className="[text-decoration-skip-ink:none] [text-underline-position:from-font] decoration-solid leading-[24px] line-through">ToDoアプリ</p>
                      </div>
                    </div>
                  </div>
                  <div className="content-stretch flex gap-[4px] items-start relative shrink-0" data-name="Container" data-node-id="1:72">
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:73">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:74">
                        <Component1 variant="5" />
                      </div>
                    </div>
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:75">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:76">
                        <Component1 variant="6" />
                      </div>
                    </div>
                  </div>
                </div>
                <div className="bg-white box-border content-stretch flex gap-[12px] items-center p-[17px] relative rounded-[10px] shrink-0 w-full" data-name="TaskEdit" data-node-id="1:77">
                  <div aria-hidden="true" className="absolute border border-[rgba(0,0,0,0.1)] border-solid inset-0 pointer-events-none rounded-[10px]" />
                  <div className="box-border content-stretch flex flex-col items-start pb-[5px] pt-[3px] px-0 relative shrink-0" data-name="Container" data-node-id="1:78">
                    <div className="bg-[#f3f3f5] relative rounded-[4px] shrink-0 size-[16px]" data-name="Checkbox" data-node-id="1:79">
                      <div aria-hidden="true" className="absolute border border-[rgba(0,0,0,0.1)] border-solid inset-0 pointer-events-none rounded-[4px] shadow-[0px_1px_2px_0px_rgba(0,0,0,0.05)]" />
                    </div>
                  </div>
                  <div className="basis-0 content-stretch flex flex-col grow items-start min-h-px min-w-px relative shrink-0" data-name="Container" data-node-id="1:80">
                    <div className="bg-[#f3f3f5] box-border content-stretch flex h-[36px] items-start overflow-clip px-[13px] py-[7.5px] relative rounded-[8px] shrink-0 w-full" data-name="Input" data-node-id="1:81">
                      <div className="box-border content-stretch flex flex-col h-full items-start overflow-auto pb-[3px] pl-0 pr-[404px] pt-px relative shrink-0" data-name="Container" data-node-id="1:82">
                        <div className="flex flex-col font-['Hiragino_Kaku_Gothic_ProN:W3',_'Noto_Sans_KR:Light',_sans-serif] justify-center leading-[0] relative shrink-0 text-[14px] text-neutral-950 text-nowrap" data-node-id="1:83" style={{ fontVariationSettings: "'wght' 300" }}>
                          <p className="leading-[normal] whitespace-pre">태스크</p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="content-stretch flex gap-[4px] items-start relative shrink-0" data-name="Container" data-node-id="1:84">
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:85">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:86">
                        <Component1 variant="7" />
                      </div>
                    </div>
                    <div className="box-border content-stretch flex items-center justify-center px-[10px] py-0 relative rounded-[8px] shrink-0 size-[36px]" data-name="Button" data-node-id="1:87">
                      <div className="relative shrink-0 size-[16px]" data-name="Component 1" data-node-id="1:88">
                        <Component1 variant="8" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="h-[20px] shrink-0 w-full" data-name="Container" data-node-id="1:90" />
            </div>
          </div>
          <div aria-hidden="true" className="absolute border border-[rgba(0,0,0,0.1)] border-solid inset-0 pointer-events-none rounded-[14px] shadow-[0px_10px_15px_-3px_rgba(0,0,0,0.1),0px_4px_6px_-4px_rgba(0,0,0,0.1)]" />
        </div>
      </div>
    </div>
  );
}
