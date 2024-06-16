## Run ELK 

* Run Docker Compose file to start ELK stack
* Potential issues and solutions:
* Elastic Search and vm.max_map_count issue
* 
```
Error: bootstrap check failure [1] of [1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]; for more information see [https://www.
elastic.co/guide/en/elasticsearch/reference/8.13/_maximum_map_count_check.html]

Run below:
==> docker run --rm -it --privileged --pid=host alpine:latest nsenter -t 1 -m -u -n -i sh

Run This: 
==> sysctl vm.max_map_count 
and if you see below
vm.max_map_count = 65530

then run this to edit it: 
==> sysctl -w vm.max_map_count=262144
==> exit
// Above should solve the issue, then run the docke compose file again

```

* General Elastic Search Queries
```shell

PUT /cucumber-test-reports
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1
  },
  "mappings": {
    "properties": {
      "scnName": { "type": "text" },
      "scnSteps": { "type": "text" },
      "scnStatus": { "type": "text" },
      "scnTags": { "type": "text" },
      "executionEnv": { "type": "text" },
      "scnEndTimeStamp": { "type": "text" },
      "scnStartTimeStamp": { "type": "text" }
    }
  }
}

GET /cucumber-test-reports/_search/
{
  "query": {
    "match_all": {}
  }
}

GET /test_reports/_search/
{
  "query": {
    "match_all": {}
  }
}

POST //_doc
{
  "scn_name": "Test Scenario 2",
  "scn_steps": "Step 1, Step 2, Step 3",
  "env": "QA",
  "tags": ["ui", "login"],
  "status": "passed",
  "time_stamp": "2024-06-12T10:00:00"
}

```