import IpAddressRange from "../type/IpAddressRange";

const INVALID_IP_ADDRESS_RANGE: IpAddressRange = {
  fromIp: null,
  toIp: null,
};

function getMaskBlocks(mask: number) {
  let result = "";
  for (let index = 0; index < 32; index++) {
    if (mask > 0) {
      result = result + "1";
    } else {
      result = result + "0";
    }
    mask--;
  }
  return [
    Number("0b" + result.substring(0, 8)),
    Number("0b" + result.substring(8, 16)),
    Number("0b" + result.substring(16, 24)),
    Number("0b" + result.substring(24, 32)),
  ];
}

function fromIp(ipBlocks: number[], maskBlocks: number[]) {
  const result = [];
  for (let index = 0; index < 4; index++) {
    result.push(String(ipBlocks[index] & maskBlocks[index]));
  }
  return result;
}

function toIP(ipBlocks: number[], maskBlocks: number[]) {
  const result = [];
  for (let index = 0; index < 4; index++) {
    let block;
    if (maskBlocks[index] === 0) {
      block = String(ipBlocks[index] | 255);
    } else {
      block = String(
        (ipBlocks[index] & maskBlocks[index]) + (255 - maskBlocks[index])
      );
    }
    result.push(String(block));
  }
  return result;
}

function isValidIpAddress(ipBlocks: string[]) {
  for (let block of ipBlocks) {
    if (isNaN(+block) || +block < 0 || +block > 255) {
      return false;
    }
  }
  return true;
}

function isValidCIDR(cidr: string): boolean {
  const ipAndMask = cidr.split("/");
  if (ipAndMask.length !== 2) {
    return false;
  }
  const ip = ipAndMask[0];
  const ipBlocks = ip.split(".");
  if (ipBlocks.length !== 4) {
    return false;
  }
  if (!isValidIpAddress(ipBlocks)) {
    return false;
  }
  if (!ipAndMask[1]) {
    return false;
  }
  const mask = +ipAndMask[1];
  return !(isNaN(mask) || mask < 0 || mask > 32);
}

export const convertCIDR = (cidr: string): IpAddressRange => {
  const isValid = isValidCIDR(cidr);
  if (!isValid) {
    return INVALID_IP_ADDRESS_RANGE;
  }
  const ipAndMask = cidr.split("/");
  const ipBlocks = ipAndMask[0].split(".").map((block) => +block);
  const maskBlocks = getMaskBlocks(+ipAndMask[1]);
  return {
    fromIp: fromIp(ipBlocks, maskBlocks).join("."),
    toIp: toIP(ipBlocks, maskBlocks).join("."),
  };
};
