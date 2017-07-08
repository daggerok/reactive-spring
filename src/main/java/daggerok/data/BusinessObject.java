package daggerok.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class BusinessObject implements Serializable {

  private static final long serialVersionUID = 476621372804881149L;

  Long id;
  final String data;
}
