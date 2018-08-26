import axios from 'axios';

import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  TEST_DATASOURCE: 'api/test-datasource'
};

const initialState = {
  loading: false,
  errorMessage: null,
  updateSuccess: false,
  updateFailure: false
};

export type DataSourceState = Readonly<typeof initialState>;

// Reducer
export default (state: DataSourceState = initialState, action): DataSourceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.TEST_DATASOURCE):
      return {
        ...initialState,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.TEST_DATASOURCE):
      return {
        ...initialState,
        loading: false,
        updateSuccess: false,
        updateFailure: true
      };
    case SUCCESS(ACTION_TYPES.TEST_DATASOURCE):
      return {
        ...initialState,
        loading: false,
        updateSuccess: true,
        updateFailure: false
      };
    default:
      return state;
  }
};

export const testDataSource = (hostname, username, password, schema) => ({
  type: ACTION_TYPES.TEST_DATASOURCE,
  payload: axios.post('api/test-datasource', { hostname, username, password, schema }),
  meta: {
    successMessage: 'Data Source Connected Successfully',
    errorMessage: 'Data Source Failed To Connect :('
  }
});
