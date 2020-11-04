# plan-elastic-search

## Requirements
Using Java, write a micro service that invokes AWS elastic search and make it available using API gateway.

Test Data - project resources/data/F_5500_2017_Latest.zip

Search should be allowed by Plan name, Sponsor name and Sponsor State
Use AWS best practices

## Data into Elastic search
  ##### 1 - Create an elastic search cluster in AWS
  ##### 2 - Load data into the above created elastic search cluster using Logstash

## Implementation

The project was implemented using SpringBoot and spring-boot-starter-data-elasticsearch for communication with the AWS elastic search cluster.
It follows a standard Java service archicture: Controller -> Service -> DAO -> Elastic search cluster

Documentation for the endpoint was created using springdoc-openapi-ui a superset of Swagger.

A docker image was created from the microservice application and pushed to dockerhub. This docker image was pull from an EC2 instance and port 8080 was exposed.
The API gateway configuration hits the above created endpoint.

Ideally this service container would be deployed to an ECS cluster and use Fargate for orchestration. But for simplicity, an API gateway end point to a docker image
running on an EC2 instance was used for this exercise.

## Search end points
https://e0zo7j4nah.execute-api.us-west-2.amazonaws.com/dev

URL Query String Parameters:
#### 1 - planName
  The PLAN_NAME field is used in the document for this search
#### 2 - sponsorName
  The SPONS_SIGNED_NAME field is used in the document for this search
#### 3 - sponsorState
  The SPONS_DFE_MAIL_US_STATE field is used in the document for this search
#### 4 - page
#### 5 - size
  The page and size parameters are used for pagination. The page parameter says how many pages to retrieve and the size is used to ask 
  for the size of the pages - in terms of items.

Note that those query string parameters above use and "AND" operator, thus they can be used for further filter down a query. That is to say that
PlanName AND sponsorName AND sponsorState is a lot more restrictive than just planName.
All parameters are optional, thus, a message and InvalidInputException is thrown when nothing gets passed thru - as we don't want to make a roundtrip search
if nothing is selected.

Example requests:

https://e0zo7j4nah.execute-api.us-west-2.amazonaws.com/dev?planName=CASINO&page=1&size=1

https://e0zo7j4nah.execute-api.us-west-2.amazonaws.com/dev?planName=WSKG&sponsorName=Greg&page=1&size=1

https://e0zo7j4nah.execute-api.us-west-2.amazonaws.com/dev?planName=WSKG&sponsorName=Greg&sponsorState&page=1&size=1

The response is a JSON response, though it is a perfectly fine JSON response it's all in caps and snake case. That's to match the document and data in elastic search.

Sample response:
```
{
  "totalItems": 2,
  "plans": [
    {
      "ACK_ID": "20180723071816P040050833901001",
      "FORM_PLAN_YEAR_BEGIN_DATE": "2017-01-01",
      "FORM_TAX_PRD": "2017-12-31",
      "TYPE_PLAN_ENTITY_CD": "2",
      "TYPE_DFE_PLAN_ENTITY_CD": null,
      "INITIAL_FILING_IND": null,
      "AMENDED_IND": null,
      "FINAL_FILING_IND": null,
      "SHORT_PLAN_YR_IND": null,
      "COLLECTIVE_BARGAIN_IND": null,
      "F5558_APPLICATION_FILED_IND": null,
      "EXT_AUTOMATIC_IND": null,
      "DFVC_PROGRAM_IND": null,
      "EXT_SPECIAL_IND": null,
      "EXT_SPECIAL_TEXT": null,
      "PLAN_NAME": "TIAA-CREF RETIREMENT PLAN FOR ALL REGULAR EMPLOYEES OF WSKG PUBLIC TELECOMMUNICATIONS COUNCIL",
      "SPONS_DFE_PN": "001",
      "PLAN_EFF_DATE": "1975-01-01",
      "SPONSOR_DFE_NAME": "WSKG PUBLIC TELECOMMUNICATIONS COUNCIL",
      "SPONS_DFE_DBA_NAME": null,
      "SPONS_DFE_CARE_OF_NAME": null,
      "SPONS_DFE_MAIL_US_ADDRESS1": "601 GATES ROAD",
      "SPONS_DFE_MAIL_US_ADDRESS2": null,
      "SPONS_DFE_MAIL_US_CITY": "VESTAL",
      "SPONS_DFE_MAIL_US_STATE": "NY",
      "SPONS_DFE_MAIL_US_ZIP": "13850",
      "SPONS_DFE_MAIL_FOREIGN_ADDR1": null,
      "SPONS_DFE_MAIL_FOREIGN_ADDR2": null,
      "SPONS_DFE_MAIL_FOREIGN_CITY": null,
      "SPONS_DFE_MAIL_FORGN_PROV_ST": null,
      "SPONS_DFE_MAIL_FOREIGN_CNTRY": null,
      "SPONS_DFE_MAIL_FORGN_POSTAL_CD": null,
      "SPONS_DFE_LOC_US_ADDRESS1": null,
      "SPONS_DFE_LOC_US_ADDRESS2": null,
      "SPONS_DFE_LOC_US_CITY": null,
      "SPONS_DFE_LOC_US_STATE": null,
      "SPONS_DFE_LOC_US_ZIP": null,
      "SPONS_DFE_LOC_FOREIGN_ADDRESS1": null,
      "SPONS_DFE_LOC_FOREIGN_ADDRESS2": null,
      "SPONS_DFE_LOC_FOREIGN_CITY": null,
      "SPONS_DFE_LOC_FORGN_PROV_ST": null,
      "SPONS_DFE_LOC_FOREIGN_CNTRY": null,
      "SPONS_DFE_LOC_FORGN_POSTAL_CD": null,
      "SPONS_DFE_EIN": "150620345",
      "SPONS_DFE_PHONE_NUM": "6077290100",
      "BUSINESS_CODE": "515100",
      "ADMIN_NAME": null,
      "ADMIN_CARE_OF_NAME": null,
      "ADMIN_US_ADDRESS1": null,
      "ADMIN_US_ADDRESS2": null,
      "ADMIN_US_CITY": null,
      "ADMIN_US_STATE": null,
      "ADMIN_US_ZIP": null,
      "ADMIN_FOREIGN_ADDRESS1": null,
      "ADMIN_FOREIGN_ADDRESS2": null,
      "ADMIN_FOREIGN_CITY": null,
      "ADMIN_FOREIGN_PROV_STATE": null,
      "ADMIN_FOREIGN_CNTRY": null,
      "ADMIN_FOREIGN_POSTAL_CD": null,
      "ADMIN_EIN": null,
      "ADMIN_PHONE_NUM": null,
      "LAST_RPT_SPONS_NAME": null,
      "LAST_RPT_SPONS_EIN": null,
      "LAST_RPT_PLAN_NUM": null,
      "ADMIN_SIGNED_DATE": "2018-07-10T04:38:11-0500",
      "ADMIN_SIGNED_NAME": "GREG CATLIN",
      "SPONS_SIGNED_DATE": "2018-07-10T04:38:11-0500",
      "SPONS_SIGNED_NAME": "GREG CATLIN",
      "DFE_SIGNED_DATE": null,
      "DFE_SIGNED_NAME": null,
      "TOT_PARTCP_BOY_CNT": "55",
      "TOT_ACTIVE_PARTCP_CNT": "28",
      "RTD_SEP_PARTCP_RCVG_CNT": null,
      "RTD_SEP_PARTCP_FUT_CNT": "35",
      "SUBTL_ACT_RTD_SEP_CNT": "63",
      "BENEF_RCVG_BNFT_CNT": null,
      "TOT_ACT_RTD_SEP_BENEF_CNT": "63",
      "PARTCP_ACCOUNT_BAL_CNT": "63",
      "SEP_PARTCP_PARTL_VSTD_CNT": null,
      "CONTRIB_EMPLRS_CNT": null,
      "TYPE_PENSION_BNFT_CODE": "2G2L2M",
      "TYPE_WELFARE_BNFT_CODE": null,
      "FUNDING_INSURANCE_IND": "1",
      "FUNDING_SEC412_IND": null,
      "FUNDING_TRUST_IND": "1",
      "FUNDING_GEN_ASSET_IND": null,
      "BENEFIT_INSURANCE_IND": "1",
      "BENEFIT_SEC412_IND": null,
      "BENEFIT_TRUST_IND": "1",
      "BENEFIT_GEN_ASSET_IND": null,
      "SCH_R_ATTACHED_IND": null,
      "SCH_MB_ATTACHED_IND": null,
      "SCH_SB_ATTACHED_IND": null,
      "SCH_H_ATTACHED_IND": null,
      "SCH_I_ATTACHED_IND": "1",
      "SCH_A_ATTACHED_IND": "1",
      "NUM_SCH_A_ATTACHED_CNT": "1",
      "SCH_C_ATTACHED_IND": null,
      "SCH_D_ATTACHED_IND": "1",
      "SCH_G_ATTACHED_IND": null,
      "FILING_STATUS": "FILING_RECEIVED",
      "DATE_RECEIVED": "2018-07-23",
      "VALID_ADMIN_SIGNATURE": "Filed with authorized/valid electronic signature",
      "VALID_DFE_SIGNATURE": null,
      "VALID_SPONSOR_SIGNATURE": "Filed with authorized/valid electronic signature",
      "ADMIN_PHONE_NUM_FOREIGN": null,
      "SPONS_DFE_PHONE_NUM_FOREIGN": null,
      "ADMIN_NAME_SAME_SPON_IND": "1",
      "ADMIN_ADDRESS_SAME_SPON_IND": null,
      "PREPARER_NAME": null,
      "PREPARER_FIRM_NAME": null,
      "PREPARER_US_ADDRESS1": null,
      "PREPARER_US_ADDRESS2": null,
      "PREPARER_US_CITY": null,
      "PREPARER_US_STATE": null,
      "PREPARER_US_ZIP": null,
      "PREPARER_FOREIGN_ADDRESS1": null,
      "PREPARER_FOREIGN_ADDRESS2": null,
      "PREPARER_FOREIGN_CITY": null,
      "PREPARER_FOREIGN_PROV_STATE": null,
      "PREPARER_FOREIGN_CNTRY": null,
      "PREPARER_FOREIGN_POSTAL_CD": null,
      "PREPARER_PHONE_NUM": null,
      "PREPARER_PHONE_NUM_FOREIGN": null,
      "TOT_ACT_PARTCP_BOY_CNT": "28",
      "SUBJ_M1_FILING_REQ_IND": null,
      "COMPLIANCE_M1_FILING_REQ_IND": null,
      "M1_RECEIPT_CONFIRMATION_CODE": null,
      "ADMIN_MANUAL_SIGNED_DATE": null,
      "ADMIN_MANUAL_SIGNED_NAME": null,
      "LAST_RPT_PLAN_NAME": null,
      "SPONS_MANUAL_SIGNED_DATE": null,
      "SPONS_MANUAL_SIGNED_NAME": null,
      "DFE_MANUAL_SIGNED_DATE": null,
      "DFE_MANUAL_SIGNED_NAM": null
    }
  ],
  "totalPages": 2,
  "currentPage": 1
}
```
