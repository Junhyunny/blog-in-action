import { withValidation } from "../enhancer/withValidation";
import TextInput from "./TextInput";
import TextArea from "./TextArea";

export const ValidationTextInput = withValidation(TextInput);
export const ValidationTextArea = withValidation(TextArea);
