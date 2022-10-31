
export interface User {
    name: string;
    age: number;
    sex: string;
    phoneNumber: string
}

export const getUsers =  async ():Promise<User[]> => {
    return [
        {name: "Junhyunny", age: 33, sex: "Male", phoneNumber: "010-1234-1234"},
        {name: "Ingang", age: 33, sex: "Female", phoneNumber: "010-1234-4321"},
        {name: "Jua", age: 12, sex: "Female", phoneNumber: "010-1234-1234"}
    ];
}