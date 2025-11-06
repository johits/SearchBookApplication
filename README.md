# 📚 SearchBookApplication  
**Clean Architecture + MVI Pattern 기반 도서 검색 애플리케이션**
<br></br>
---

> ⚠️ **주의:** 본 프로젝트는 보안상의 이유로 `local.properties` 파일에 Kakao API Key가 포함되어 있으며, 실제 실행은 불가능합니다.  
> 코드 구조 및 아키텍처 참고용 예제 프로젝트입니다.

<br></br>
## 📱 앱 시연
![첫번째 (2)](https://github.com/user-attachments/assets/50c6d5e7-d708-4f35-bc9b-790b8ad9b4c3)
![두번째 (2)](https://github.com/user-attachments/assets/2f6591db-68e1-457d-b867-379dd6a98691)
![세번째 (1)](https://github.com/user-attachments/assets/0fbf47c7-e1e4-4f92-a34d-b69f93e57d8e)


## 🧩 프로젝트 개요

`SearchBookApplication`은 **Kakao Books API**를 활용하여  
도서를 검색하고 마음에 드는 도서를 북마크할 수 있는 **도서 검색 앱**입니다.

> ✨ 주요 기능  
> - 도서 검색 (제목 / 저자 기반)  
> - 정렬 (검색: 정확도순·발간일순 / 즐겨찾기: 제목 오름·내림차순)  
> - 북마크 등록 및 삭제  
> - Room 기반 로컬 저장소로 북마크 유지  
> - 검색 상태 (로딩 / 실패 / 성공) 시각화  

<br></br>

## 🏗 아키텍처 개요
본 프로젝트는 **Clean Architecture**를 기반으로 설계되었으며,  
**MVI (Model–View–Intent)** 패턴을 적용해 **단방향 상태 흐름**을 유지합니다.  
아래는 전체 모듈 간의 의존성 구조입니다.
<p align="left"><img width="580" height="527" alt="module_dependency" src="https://github.com/user-attachments/assets/dce76792-1a1c-4128-8dac-95150ff7536e"/></p>

## 🧱 계층별 역할

| Layer | 역할 |
|-------|------|
| **UI** | Jetpack Compose 기반 화면 구성 |
| **Presentation** | ViewModel + Reducer + Contract (MVI 구조) |
| **Domain** | UseCase 및 Entity 정의, 비즈니스 로직 수행 |
| **Data** | Repository 구현, Local/Remote DataSource 통합 |
| **Remote** | Retrofit + OkHttp로 API 통신 담당 |
| **Local** | Room DB 기반 데이터 영구 저장 |
| **Data-Resource** | 데이터 상태(Loading/Success/Error) 공통 관리<br>**Domain 확장 유틸리티 계층**, 외부 의존이 아닌 공통 상태 모델로 취급 |
| **App** | Application Entry Point, Hilt 초기화 및 DI 구성 |
| **buildSrc** | Gradle Kotlin DSL 기반 의존성 및 버전 관리 |

<br></br>

## 🧠 MVI 패턴 구조

MVI는 **단방향 데이터 흐름(Unidirectional Data Flow)** 을 유지하여  
UI 상태의 예측 가능성과 안정성을 높입니다.

```plaintext
UI → Event → ViewModel → Reducer → State → UI
                   ↓
                Effect (ex. Toast, Navigation)
```
<br></br>
## 🧩 기술 스택

| 분류 | 기술 | 설명 |
|:------|:------|:------|
| **UI** | Jetpack Compose, Material3 | 선언형 UI, 반응형 상태 관리 |
| **DI** | Hilt (Dagger) | 의존성 주입 및 전역 Scope 관리 |
| **Architecture** | Clean Architecture, MVI Pattern | 계층 분리 + 상태 기반 구조 |
| **Network** | Retrofit, OkHttp3, Gson | Kakao Books API 연동 |
| **Database** | Room, DataStore | 북마크 영구 저장 |
| **Async** | Kotlin Coroutines, Flow | 비동기 스트림 처리 |
| **Build** | Gradle Kotlin DSL, Version Catalog, buildSrc | 멀티모듈 버전 관리 |
| **Language** | Kotlin | 100% Kotlin 기반 |





