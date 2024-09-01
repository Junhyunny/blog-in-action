Feature("login");

Scenario("When login Then I can see Hello World ", ({ I, loginAs }) => {
  loginAs("JUNHYUNNY");
  I.wait(1.5);

  I.see("Hello World");
});
