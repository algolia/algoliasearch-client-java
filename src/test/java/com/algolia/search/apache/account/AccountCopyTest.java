package com.algolia.search.apache.account;

import com.algolia.search.integration.IntegrationTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class AccountCopyTest extends com.algolia.search.integration.account.AccountCopyTest {
  AccountCopyTest() {
    super(IntegrationTestExtension.searchClient, IntegrationTestExtension.searchClient2);
  }
}
