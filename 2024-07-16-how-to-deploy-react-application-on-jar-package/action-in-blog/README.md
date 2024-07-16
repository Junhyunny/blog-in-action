
# TAS(Tanzu Application Service) playground

Before start this example, please start the [CF server](https://github.com/making/bosh-ops/actions/workflows/bosh-start.yml) what Maki-san set up for us. When finish trial this example then stop [the PCF server](https://github.com/making/bosh-ops/actions/workflows/bosh-stop.yml) please to save cost.

## Install

- install cf CLI version 7 on MacOS

```
brew install cloudfoundry/tap/cf-cli@7
```

- install cf CLI version 8 on MacOS

```
brew install cloudfoundry/tap/cf-cli@8
```

- check installation cf

```
cf --help
```

## Login PCF

- connect to api end-point

```
cf login -a https://api.sys.sandbox.aws.maki.lol
```

- login command

```
cf login
```

## Setup Org and Space

- current ORG name is "dig"
- current SPACE name is "demo"

```
export ORG_NAME="dig"
export SPACE_NAME="demo"
cf target -o "$ORG_NAME"
cf target -s "$SPACE_NAME"
```

- if you want to create ORG, SPACE then use this command

```
cf create-org "$ORG_NAME"
cf target -o "$ORG_NAME"
cf create-space "$SPACE_NAME"
cf target -s "$SPACE_NAME"
```

## Frontend

Currently, frontend application uses `staticfile_buildpack`. Request backend application directly and make CORS policy rules in backend side. I am preparing to deploy with `nginx_buildpack` and reverse proxy setting.

- move to the frontend directory

```
cd frontend
```

- install dependencies

```
npm run install
```

- build application

```
npm run build
```

- deploy frontend application

```
cf push
```

## Backend

- move to the backend directory

```
cd backend
```

- build executable jar

```
./gradlew bootJar
```

- deploy backend application

```
cf push
```

## Combine frontend and backend

You can also deploy jar application combined frontend and backend. This works properly only in a `combined-build` branch. First change the branch please.

```
git checkout combined-build
```

Run deploy script from root directory.

```
sh deploy.sh
```

#### Other Reference

You can also use this playground. You have to connect with VMWare VPN not Broadcom.

- <https://confluence.eng.vmware.com/pages/viewpage.action?spaceKey=CNA&title=TAS+and+TAP+playground+-+Dhaka+Foundation>
