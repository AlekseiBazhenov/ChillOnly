package ru.modernsoft.chillonly.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.modernsoft.chillonly.CoroutineTestRule
import ru.modernsoft.chillonly.TestUtils
import ru.modernsoft.chillonly.TestUtils.fromJson
import ru.modernsoft.chillonly.business.use_cases.UseCaseNoParams
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.models.Station

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StationsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: StationsViewModel

    @Mock
    private lateinit var useCase: UseCaseNoParams<List<Station>>

    @Suppress("UNCHECKED_CAST")
    @Mock
    private lateinit var observer: Observer<Resource<List<Station>>>

    @Before
    fun setup() {
        viewModel = StationsViewModel(useCase, coroutineTestRule.testDispatcher)
    }

    @After
    fun after() {
        verifyNoMoreInteractions(observer)
        verifyNoMoreInteractions(useCase)
    }

    @Test
    fun `should get stations`() {
        val json = TestUtils.getJsonString(("json/stations.json"))
        val data = json.fromJson<List<Station>>() ?: emptyList()

        runBlockingTest {
            `when`(useCase.doWork()).thenReturn(data)

            val liveData = viewModel.getStations()
            liveData.observeForever(observer)

            verify(useCase).doWork()

            verify(observer).onChanged(Resource.loading(null))
            verify(observer).onChanged(Resource.success(data))

            liveData.removeObserver(observer)

            assertThat(liveData.value?.data?.size, `is`(4))
        }
    }
}