## 📱 E-Commerce

최신 Android 개발 기술을 적용한 E-Commerce 애플리케이션입니다.	
E-Commerce 핵심 기술들을 구현하였으며, **실제 서비스 아키텍처 구조로 설계**하였으며 성능·확장성·유지보수성을 모두 고려하여 개발하였습니다.

## ✨ Features
- `로그인(Login)`: 사용자 로그인, Kakao 연동
- `홈(Home)`: 메인 홈 화면, 배너	
- `상품 검색(Search)`: Debounce 적용, 최근 검색어, 상품 검색, 검색 결과 페이지
- `상품 상세(Product)`: 상품 상세 화면, WebView 적용
- `장바구니 관리(Cart)`: DataStore(Proto) 적용, 사용자별 DataStore 관리	
- `카테고리/상품 상세(Caregory)`:  카테고리별 상품 조회
- `마이페이지(Mypage)`:  사용자 정보	

## 🧱 Architecture
**Multi-Module Clean Arch**기반으로 설계하여, 모듈 간 결합도를 낮추고 기능 확장성과 유지보수성을 극대화했습니다.  
`core` 모듈은 공통 로직과 base 시스템을 제공하고, `feature` 모듈은 각각 독립적인 도메인을 담당하여 기능 단위로 관리됩니다.

```plaintext
app/
├── core/
│ ├── android/
│ ├── auth/
│ ├── database/
│ ├── domain/
│ ├── navigation/
│ └── notification/
└── feature/
├── home/
├── category/
├── product/
├── search/
├── cart/
├── login/
└── mypage/
```

## 🛠️ Tech Stack
- **Language**: Kotlin 100%	
- **UI Framework**: Jetpack Compose	
- **Architecture**: Multi-Module Clean Architecture	
- **Dependency Injection**: Hilt	
- **Asynchronous**: Coroutines + Flow
- **Data** : RoomDB, DataStore(Proto)
