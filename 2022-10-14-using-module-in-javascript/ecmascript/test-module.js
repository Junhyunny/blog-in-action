import { sum, substract } from "./es-math-first.js";
import * as customMathFirst from "./es-math-first.js";

import customMathSecond from "./es-math-second.js";

console.log(`5 + 3 = ${sum(5, 3)}`);
console.log(`5 - 3 = ${substract(5, 3)}`);

console.log(`5 + 3 = ${customMathFirst.sum(5, 3)}`);
console.log(`5 - 3 = ${customMathFirst.substract(5, 3)}`);

console.log(`5 * 3 = ${customMathSecond.multiply(5, 3)}`);
console.log(`5 / 3 = ${customMathSecond.divide(5, 3)}`);
