import React, { useCallback, useState } from "react";
import "./App.css";

import DateInput from "./component/DateInput";
import EmailInput from "./component/EmailInput";
import IpInput from "./component/IpInput";
import PhoneContactInput from "./component/PhoneContactInput";
import { ValidationTextArea } from "./component/ValidationComponents";

type Data = {
  date: string;
  email: string;
  ipAddress: string;
  phoneContact: string;
  etc: string;
};

const DEFAULT_DATA: Data = {
  date: "",
  email: "",
  ipAddress: "",
  phoneContact: "",
  etc: "",
};

function App() {
  const [data, setData] = useState<Data>(DEFAULT_DATA);

  const changeSate = useCallback(
    (key: string, value: string) => {
      setData({
        ...data,
        [key]: value,
      });
    },
    [data]
  );

  return (
    <div className="App">
      <label>
        날짜
        <DateInput
          value={data.date}
          onValueChange={(value) => changeSate("date", value)}
        />
      </label>
      <label>
        이메일
        <EmailInput
          value={data.email}
          onValueChange={(value) => changeSate("email", value)}
        />
      </label>
      <label>
        IP
        <IpInput
          value={data.ipAddress}
          onValueChange={(value) => changeSate("ipAddress", value)}
        />
      </label>
      <label>
        연락처
        <PhoneContactInput
          value={data.phoneContact}
          onValueChange={(value) => changeSate("phoneContact", value)}
        />
      </label>
      <label>
        기타
        <ValidationTextArea
          value={data.etc}
          onValueChange={(value) => changeSate("etc", value)}
          isValidate={(value: string) => value?.length <= 10}
          errorMessage="10글자 이상 입력할 수 없습니다."
        />
      </label>
    </div>
  );
}

export default App;
