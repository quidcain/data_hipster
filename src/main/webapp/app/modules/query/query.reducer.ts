import axios from 'axios';

import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  RUN_QUERY: 'api/query',
  SCHEDULE_QUERY: 'api/schedule'
};

const initialState = {
  loading: false,
  errorMessage: null,
  updateSuccess: false,
  updateFailure: false,
  columns: [],
  rows: []
};

export type QueryState = Readonly<typeof initialState>;

// Reducer
export default (state: QueryState = initialState, action): QueryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: false,
        updateFailure: true,
        columns: [],
        rows: []
      };
    case SUCCESS(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: true,
        updateFailure: false,
        columns: action.payload.data.columns || [],
        rows: action.payload.data.rows || []
      };
    default:
      return state;
  }
};

export const runQuery = query => ({
  type: ACTION_TYPES.RUN_QUERY,
  payload: axios.post('api/query', { query, queryType: 'SQL' }),
  meta: {
    successMessage: 'Query executed',
    errorMessage: 'Error Running Query no data will be returned.'
  }
});

export const scheduleQuery = (timeMeasure, frequencyValue) => ({
  type: ACTION_TYPES.SCHEDULE_QUERY,
  payload: axios.put('api/schedule', { timeMeasure, frequencyValue }).then(res => {
    const persons = res.data;
    // this.setState({ columns: res.data.columns, rows: res.data.rows });
  }),
  meta: {
    successMessage: '<strong>Query scheduled</strong>',
    errorMessage: '<strong>Error Scheduling Query</strong> no schedule was created.'
  }
});
