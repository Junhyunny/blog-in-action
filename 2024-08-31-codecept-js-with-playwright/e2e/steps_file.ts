import Secret = CodeceptJS.Secret;

export = function () {
  return actor({
    login(username: Secret, password: Secret) {
      this.amOnPage("/login");
      this.waitForText("Please sign in", 1.5);
      this.fillField("input[name=username]", username);
      this.fillField("input[name=password]", password);
      this.click("Sign in");
    },
  });
};
