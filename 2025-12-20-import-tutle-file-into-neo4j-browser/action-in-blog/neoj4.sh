#!/bin/bash

docker run \
    --name neo4j \
    -p7474:7474 -p7687:7687 \
    -v ./data:/data \
    -v ./logs:/logs \
    -v ./import:/var/lib/neo4j/import \
    -v ./plugins:/plugins \
    --env NEO4J_PLUGINS='["apoc"]' \
    --env NEO4J_server_directories_import=import \
    --env NEO4J_dbms_security_allow__csv__import__from__file__urls=true \
    --env NEO4J_AUTH=neo4j/cool-culture-vodka-beach-eric-9620 \
    --env NEO4J_dbms_security_procedures_unrestricted="apoc.*,n10s.*" \
    --env NEO4J_dbms_security_procedures_allowlist="apoc.*,n10s.*" \
    neo4j:latest