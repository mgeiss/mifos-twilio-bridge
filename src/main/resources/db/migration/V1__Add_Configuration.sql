CREATE TABLE sms_bridge_config (
  id                      BIGINT IDENTITY,
  tenant_id               VARCHAR(32)  NOT NULL,
  api_key                 VARCHAR(32)  NOT NULL,
  endpoint                VARCHAR(256) NOT NULL,
  mifos_token             VARCHAR(256) NOT NULL,
  sms_provider            VARCHAR(32)  NOT NULL,
  sms_provider_account_id VARCHAR(256) NOT NULL,
  sms_provider_token      VARCHAR(32)  NOT NULL,
  phone_no                VARCHAR(255) NOT NULL,
  UNIQUE (tenant_id)
);