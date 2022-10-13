import {selector} from "recoil";
import {isLoadingAtom} from "./UAtoms";

export const isLoadingSelector = selector({
    key: 'isLoadingSelector',
    get: ({get}) => {
        return get(isLoadingAtom)
    }
})