package br.com.confitec.teste.service;

import java.math.BigDecimal;

public interface DescontoService {

	/**
	 * Calcula o valor final de uma compra, aplicando desconto de acordo com a
	 * quantidade de parcelas, baseado na seguinte regra:
	 * <p>
	 * <table border="1">
	 * <tr>
	 * <th>Quantidade de parcelas</th>
	 * <th>Percentual de desconto</th>
	 * </tr>
	 * <tr>
	 * <td><center>1</center></td>
	 * <td><center>10%</center></td>
	 * </tr>
	 * <tr>
	 * <td><center>2</center></td>
	 * <td><center>8%</center></td>
	 * </tr>
	 * <tr>
	 * <td><center>3</center></td>
	 * <td><center>6%</center></td>
	 * </tr>
	 * <tr>
	 * <td><center>4</center></td>
	 * <td><center>4%</center></td>
	 * </tr>
	 * <tr>
	 * <td><center>5</center></td>
	 * <td><center>2%</center></td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td><center>>5</center></td>
	 * <td><center>0%</center></td>
	 * </tr>
	 * </table>
	 *
	 * @param valorCompra       Valor da compra
	 * @param quantidadeParcela Quantidade de parcelas
	 *
	 * @return Valor final da compra
	 */
	BigDecimal calcularDesconto(final BigDecimal valorCompra, int quantidadeParcela);
}