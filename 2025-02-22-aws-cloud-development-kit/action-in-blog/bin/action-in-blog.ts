#!/usr/bin/env node
import * as cdk from "aws-cdk-lib";
import { ActionInBlogStack } from "../lib/action-in-blog-stack";
import * as dotenv from "dotenv";

dotenv.config();

const app = new cdk.App();

new ActionInBlogStack(app, "ActionInBlogStack", {
  stackName: "action-in-blog-dev",
  env: {
    account: process.env.ACCOUNT,
    region: process.env.REGION,
  },
});
