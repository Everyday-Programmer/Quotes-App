# Gitleaks Audit Report

This audit report summarizes the findings from a Gitleaks scan, detailing instances of sensitive data exposure due to generic API keys and private keys found in the source code. Each finding is documented with the necessary details, recommended remediation actions, and links to relevant resources.

---

## Vulnerability No. 1

| Key              | Value                                                                        |
|------------------|------------------------------------------------------------------------------|
| RuleID           | generic-api-key                                                              |
| Description      | Detected a Generic API Key, potentially exposing access to various services and sensitive operations. |
| Start Line       | 6                                                                            |
| End Line         | 6                                                                            |
| Start Column     | 5                                                                            |
| End Column       | 43                                                                           |
| Match            | `zapApiKey: "v9dn0balpqas1pcc281tn5ood1"`                                   |
| Secret           | `v9dn0balpqas1pcc281tn5ood1`                                                |
| File             | `config/env/test.js`                                                        |
| Commit           | `57f638b3c4495c14327a06c742c1de8d291a02b0`                                 |
| Author           | Kim Carter                                                                  |
| Date             | 2016-05-28T08:55:17Z                                                        |
| Link             | [View Code](https://github.com/OWASP/NodeGoat/blob/57f638b3c4495c14327a06c742c1de8d291a02b0/config/env/test.js#L6) |
| Entropy          | 4.0560207                                                                   |

**Affected File(s):**  
`config/env/test.js`

**Details of the Vulnerability:**  
Detected a Generic API Key, potentially exposing access to various services and sensitive operations.

**Recommended Fix:**  
Rotate the exposed API key and remove it from the codebase. Store secrets in environment variables or a secret manager.

---

## Vulnerability No. 2

| Key              | Value                                                                        |
|------------------|------------------------------------------------------------------------------|
| RuleID           | generic-api-key                                                              |
| Description      | Detected a Generic API Key, potentially exposing access to various services and sensitive operations. |
| Start Line       | 5                                                                            |
| End Line         | 5                                                                            |
| Start Column     | 6                                                                            |
| End Column       | 44                                                                           |
| Match            | `zapApiKey: "v9dn0balpqas1pcc281tn5ood1"`                                   |
| Secret           | `v9dn0balpqas1pcc281tn5ood1`                                                |
| File             | `config/env/test.js`                                                        |
| Commit           | `32f597adacbc351f134d05ba7661afeacd233a78`                                 |
| Author           | Kim Carter                                                                  |
| Date             | 2016-05-08T09:08:34Z                                                        |
| Link             | [View Code](https://github.com/OWASP/NodeGoat/blob/32f597adacbc351f134d05ba7661afeacd233a78/config/env/test.js#L5) |
| Entropy          | 4.0560207                                                                   |

**Affected File(s):**  
`config/env/test.js`  

**Details of the Vulnerability:**  
Detected a Generic API Key, potentially exposing access to various services and sensitive operations.

**Recommended Fix:**  
Rotate the exposed API key and remove it from the codebase. Store secrets in environment variables or a secret manager.

---

## Vulnerability No. 3

| Key              | Value                                                                        |
|------------------|------------------------------------------------------------------------------|
| RuleID           | generic-api-key                                                              |
| Description      | Detected a Generic API Key, potentially exposing access to various services and sensitive operations. |
| Start Line       | 18                                                                           |
| End Line         | 18                                                                           |
| Start Column     | 5                                                                            |
| End Column       | 43                                                                           |
| Match            | `zapApiKey: "v9dn0balpqas1pcc281tn5ood1"`                                   |
| Secret           | `v9dn0balpqas1pcc281tn5ood1`                                                |
| File             | `config/env/development.js`                                                 |
| Commit           | `32f597adacbc351f134d05ba7661afeacd233a78`                                 |
| Author           | Kim Carter                                                                  |
| Date             | 2016-05-08T09:08:34Z                                                        |
| Link             | [View Code](https://github.com/OWASP/NodeGoat/blob/32f597adacbc351f134d05ba7661afeacd233a78/config/env/development.js#L18) |
| Entropy          | 4.0560207                                                                   |

**Affected File(s):**  
`config/env/development.js`

**Details of the Vulnerability:**  
Detected a Generic API Key, potentially exposing access to various services and sensitive operations.

**Recommended Fix:**  
Rotate the exposed API key and remove it from the codebase. Store secrets in environment variables or a secret manager.

---

## Vulnerability No. 4

| Key              | Value                                                                        |
|------------------|------------------------------------------------------------------------------|
| RuleID           | private-key                                                                  |
| Description      | Identified a Private Key, which may compromise cryptographic security and sensitive data encryption. |
| Start Line       | 1                                                                            |
| End Line         | 15                                                                           |
| Start Column     | 1                                                                            |
| End Column       | 30                                                                           |
| Match            | `-----BEGIN RSA PRIVATE KEY-----`                                           |
| Secret           | `<Confidential>`                                                              |
| File             | `artifacts/cert/server.key`                                                 |
| Commit           | `c5f265d7a32f4a3d6478c1badedde944219d48b5`                                 |
| Author           | Jesús Pérez                                                               |
| Date             | 2015-05-12T22:41:47Z                                                        |
| Link             | [View Code](https://github.com/OWASP/NodeGoat/blob/c5f265d7a32f4a3d6478c1badedde944219d48b5/artifacts/cert/server.key#L1-L15) |
| Entropy          | 5.9881105                                                                   |

**Affected File(s):**  
`artifacts/cert/server.key`

**Details of the Vulnerability:**  
Identified a Private Key, which may compromise cryptographic security and sensitive data encryption.

**Recommended Fix:**  
Rotate the private key immediately, delete the compromised key from the codebase, and ensure secure storage practices are followed.

---

## Vulnerability No. 5

| Key              | Value                                                                        |
|------------------|------------------------------------------------------------------------------|
| RuleID           | private-key                                                                  |
| Description      | Identified a Private Key, which may compromise cryptographic security and sensitive data encryption. |
| Start Line       | 1                                                                            |
| End Line         | 9                                                                            |
| Start Column     | 1                                                                            |
| End Column       | 30                                                                           |
| Match            | `-----BEGIN RSA PRIVATE KEY-----`                                           |
| Secret           | `<Confidential>`                                                              |
| File             | `app/cert/key.pem`                                                          |
| Commit           | `e0045e653a53b846c22aa261cc80989bfc4ddb24`                                 |
| Author           | Chetan Karande                                                              |
| Date             | 2014-05-06T03:23:32Z                                                        |
| Link             | [View Code](https://github.com/OWASP/NodeGoat/blob/e0045e653a53b846c22aa261cc80989bfc4ddb24/app/cert/key.pem#L1-L9) |
| Entropy          | 5.8680615                                                                   |

**Affected File(s):**  
`app/cert/key.pem`

**Details of the Vulnerability:**  
Identified a Private Key, which may compromise cryptographic security and sensitive data encryption.

**Recommended Fix:**  
Rotate the private key immediately, delete the compromised key from the codebase, and ensure secure storage practices are followed.

---

## Summary of Findings

| Vulnerability No. | RuleID          | Affected File               | Severity       |
|------------------|----------------|-----------------------------|----------------|
| 1                | generic-api-key | config/env/test.js          | High           |
| 2                | generic-api-key | config/env/test.js          | High           |
| 3                | generic-api-key | config/env/development.js   | High           |
| 4                | private-key     | artifacts/cert/server.key    | Critical       |
| 5                | private-key     | app/cert/key.pem            | Critical       |

This report highlights critical vulnerabilities found in the codebase. Immediate actions should be taken to remediate these findings to safeguard sensitive data and maintain application security.
