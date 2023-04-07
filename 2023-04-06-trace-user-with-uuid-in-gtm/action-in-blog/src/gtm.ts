window.dataLayer = window.dataLayer
  ? window.dataLayer
  : {
      push: (data) => {},
    };

const originPush = window.dataLayer.push.bind(window.dataLayer);
Object.defineProperty(window.dataLayer, "push", {
  get() {
    return this.value ? this.value : originPush;
  },
  set(value) {
    this.value = value;
  },
});

export const overrideDataLayer = (txId: string) => {
  const overridePush = (push: (data: any) => void) => (value: any) => {
    push({ ...value, txId });
  };
  window.dataLayer.push = overridePush(originPush);
  window.dataLayer.push({ event: "app_start" });
};
