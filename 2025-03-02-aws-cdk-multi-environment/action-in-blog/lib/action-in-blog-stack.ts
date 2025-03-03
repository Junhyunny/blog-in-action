import * as cdk from "aws-cdk-lib";
import { CfnOutput, Stack } from "aws-cdk-lib";
import { Construct } from "constructs";
import { Peer, Port } from "aws-cdk-lib/aws-ec2";
import { ApplicationLoadBalancer } from "aws-cdk-lib/aws-elasticloadbalancingv2";
import { InstanceIdTarget } from "aws-cdk-lib/aws-elasticloadbalancingv2-targets";
import { readFileSync } from "node:fs";

export class ActionInBlogStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const stackName = Stack.of(this).stackName;

    const vpc = new cdk.aws_ec2.Vpc(this, `${stackName}-vpc`, {
      vpcName: `${stackName}-vpc`,
      maxAzs: 2,
    });

    const { albSecurityGroup, ec2SecurityGroup } = this.createSecurityGroup(
      stackName,
      vpc,
    );

    const ec2 = this.createEc2Instance(stackName, vpc, ec2SecurityGroup);

    const { alb, listener } = this.createLoadBalancerAndListener(
      stackName,
      vpc,
      albSecurityGroup,
    );

    listener.addTargets(`${stackName}-listener-tg`, {
      targetGroupName: `${stackName}-listener-tg`,
      port: 80,
      targets: [new InstanceIdTarget(ec2.instanceId, 80)],
    });

    new CfnOutput(this, "output-alb-dns-name", {
      value: alb.loadBalancerDnsName,
    });
  }

  createSecurityGroup(stackName: string, vpc: cdk.aws_ec2.Vpc) {
    const albSecurityGroup = new cdk.aws_ec2.SecurityGroup(
      this,
      `${stackName}-alb-sg`,
      {
        vpc,
        securityGroupName: `${stackName}-alb-sg`,
        description: "Allow HTTP traffic to ALB",
      },
    );
    albSecurityGroup.addIngressRule(
      Peer.ipv4("1.239.221.167/32"),
      Port.tcp(80),
      "Allow HTTP traffic to ALB",
    );
    const ec2SecurityGroup = new cdk.aws_ec2.SecurityGroup(
      this,
      `${stackName}-ec2-sg`,
      {
        vpc: vpc,
        securityGroupName: `${stackName}-ec2-sg`,
        description: "Allow all traffic in VPC to EC2",
      },
    );
    ec2SecurityGroup.addIngressRule(
      Peer.ipv4(vpc.vpcCidrBlock),
      Port.allTraffic(),
      "Allow all traffic in VPC to EC2",
    );
    return { albSecurityGroup, ec2SecurityGroup };
  }

  createEc2Instance(
    stackName: string,
    vpc: cdk.aws_ec2.Vpc,
    securityGroup: cdk.aws_ec2.SecurityGroup,
  ) {
    // cdk.json 파일에 위치한 환경 정보, 스택 이름 정보 객체를 조회한다.
    const ec2Configs = this.node.tryGetContext("ec2Instances");

    // CLI 명령어를 통해 주입받은 컨텍스트 값을 사용한다.
    const selectedEnv = this.node.tryGetContext("env") || "dev";

    // 선택한 EC2 인스턴스 설정을 사용한다.
    const selectedEc2Config = ec2Configs[selectedEnv];

    const cacheAmi = cdk.aws_ec2.MachineImage.lookup({
      name: "al2023-ami-*-x86_64",
      owners: ["amazon"],
    });
    const ec2 = new cdk.aws_ec2.Instance(this, `${stackName}-ec2`, {
      vpc,
      instanceName: `${stackName}-ec2`,
      instanceType: new cdk.aws_ec2.InstanceType(
        selectedEc2Config["instanceType"],
      ),
      machineImage: cacheAmi,
      securityGroup: securityGroup,
      vpcSubnets: {
        subnets: vpc.privateSubnets,
      },
    });
    const userDataScript = readFileSync("./script/user-data.sh", "utf8");
    ec2.addUserData(userDataScript);
    return ec2;
  }

  createLoadBalancerAndListener(
    stackName: string,
    vpc: cdk.aws_ec2.Vpc,
    securityGroup: cdk.aws_ec2.SecurityGroup,
  ) {
    const alb = new ApplicationLoadBalancer(this, `${stackName}-alb`, {
      vpc,
      loadBalancerName: `${stackName}-alb`,
      internetFacing: true,
      securityGroup: securityGroup,
    });
    const listener = alb.addListener(`${stackName}-alb-listener`, {
      port: 80,
      open: true,
    });
    return { alb, listener };
  }
}
