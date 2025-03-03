#!/usr/bin/env node
import * as cdk from "aws-cdk-lib";
import { ActionInBlogStack } from "../lib/action-in-blog-stack";

const app = new cdk.App();

// cdk.json 파일에 위치한 환경 정보, 스택 이름 정보 객체를 조회한다.
const environments = app.node.tryGetContext("environments");
const stackNames = app.node.tryGetContext("stackNames");

// CLI 명령어를 통해 주입받은 컨텍스트 값을 사용한다.
const selectedEnv = app.node.tryGetContext("env") || "dev";

// 선택한 환경 설정을 사용한다.
const selectedEnvConfig = environments[selectedEnv];

new ActionInBlogStack(app, "ActionInBlogStack", {
  stackName: stackNames[selectedEnv],
  env: {
    account: selectedEnvConfig["account"],
    region: selectedEnvConfig["region"],
  },
});
